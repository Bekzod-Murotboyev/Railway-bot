package uz.pdp.eticketrailway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.eticketrailway.entity.User;
import uz.pdp.eticketrailway.feign.RailwayFeign;
import uz.pdp.eticketrailway.feign.TelegramFeign;
import uz.pdp.eticketrailway.model.place.Place;
import uz.pdp.eticketrailway.model.railway.CarsItem;
import uz.pdp.eticketrailway.model.railway.Station;
import uz.pdp.eticketrailway.model.railway.TariffItem;
import uz.pdp.eticketrailway.payload.UserInfoDTO;
import uz.pdp.eticketrailway.payload.place.PlaceDTO;
import uz.pdp.eticketrailway.payload.place.Train;
import uz.pdp.eticketrailway.payload.station.DirectionItem;
import uz.pdp.eticketrailway.payload.station.StationDTO;
import uz.pdp.eticketrailway.utils.enums.BotState;
import uz.pdp.eticketrailway.utils.enums.DirectionType;
import uz.pdp.eticketrailway.utils.enums.Region;
import uz.pdp.eticketrailway.utils.interfaces.Url;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import static uz.pdp.eticketrailway.utils.enums.BotState.*;
import static uz.pdp.eticketrailway.utils.interfaces.Constant.*;

@Service
@RequiredArgsConstructor
public class BotService {

    private final TelegramFeign telegramFeign;

    private final RailwayFeign railwayFeign;

    private final InlineKeyboardService inlineKeyboard;

    private final UserService userService;


    public void deleteMessage(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        telegramFeign.deleteMessage(new DeleteMessage(message.getChatId().toString(), message.getMessageId()));
    }

    public void removeKeyBoardMarkup(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();

        boolean isSaved = true;
        if (message.hasContact())
            isSaved = userService.savePhoneNumber(message.getChatId().toString(), message.getContact().getPhoneNumber()) != null;
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), message.hasContact() ? isSaved ? SUCCESS : PHONE_ALREADY_EXIST : "⬅️⬅️⬅️");
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setRemoveKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        telegramFeign.sendMessage(sendMessage);
    }

    public UserInfoDTO getAndCheck(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        User user = userService.getAndCheck(message);
        return new UserInfoDTO(user.getState(), user.isActive());
    }

    public BotState getState(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        String chatId = message.getChatId().toString();
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            if (data.startsWith(FROM_REGION)) {
                userService.saveFromRegion(chatId, data.substring(FROM_REGION.length()));
                return GET_TO;
            }
            if (data.startsWith(TO_REGION)) {
                userService.saveToRegion(chatId, data.substring(TO_REGION.length()));
                return GET_DATE;
            }
            if (data.startsWith(DATE)) {
                LocalDate date = LocalDate.parse(data.substring(DATE.length()));
                if (date.isBefore(LocalDate.now().minusDays(1))) {
                    getWarningSend(update, WRONG_DATE, null);
                    return SKIP_ACTION;
                }
                userService.saveDate(chatId, date);
                return SHOW_STATIONS;
            }
            if (data.startsWith(TRAIN)) {
                if (!userService.checkRegister(chatId))
                    return SEND_WARNING;
                userService.saveTrainNumber(chatId, data.substring(TRAIN.length()));
                return SHOW_TRAIN;
            }
            if (data.startsWith(CAR)) {
                userService.saveCarNumber(chatId, data.substring(CAR.length()));
                return SHOW_CAR;
            }
        }
        return MAIN_MENU_EDIT;
    }


    public void switchDate(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        boolean isPrev = query.getData().startsWith(PREV);
        LocalDate date = LocalDate.parse(query.getData().substring(isPrev ? PREV.length() : NEXT.length()));
        getDate(update, isPrev ? date.minusMonths(1) : date.plusMonths(1));
    }

    public void getMainMenuSend(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), MENU_TEXT);
        sendMessage.setReplyMarkup(inlineKeyboard.createMarkup(List.of(
                List.of(SEARCH, SETTINGS)
        )));
        telegramFeign.sendMessage(sendMessage);
    }

    public void getMainMenuEdit(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(MENU_TEXT);
        editMessageText.setReplyMarkup(inlineKeyboard.createMarkup(List.of(
                List.of(SEARCH, SETTINGS)
        )));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        telegramFeign.editMessageText(editMessageText);
    }

    public void saveUserData(Update update, BotState state) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        userService.saveUserData(message.getChatId().toString(), state);
    }

    public void getSettingsMenuSend(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), MENU_TEXT);
        sendMessage.setReplyMarkup(inlineKeyboard.createMarkup(List.of(
                List.of(REGISTER),
                List.of(BACK_TO_MAIN_MENU)
        )));
        sendMessage.setChatId(message.getChatId().toString());
        telegramFeign.sendMessage(sendMessage);
    }

    public void getSettingsMenuEdit(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(MENU_TEXT);
        editMessageText.setReplyMarkup(inlineKeyboard.createMarkup(List.of(
                List.of(REGISTER),
                List.of(BACK_TO_MAIN_MENU)
        )));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        telegramFeign.editMessageText(editMessageText);
    }

    public void getMenuRegister(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), message.getChat().getFirstName() + ENTER_PHONE_NUMBER_TEXT);
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        markup.setKeyboard(rowList);

        KeyboardButton number = new KeyboardButton(MY_PHONE_NUMBER);
        number.setRequestContact(true);
        KeyboardButton back = new KeyboardButton(BACK);
        rowList.add(new KeyboardRow(Collections.singletonList(number)));
        rowList.add(new KeyboardRow(Collections.singletonList(back)));


        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);
        sendMessage.setReplyMarkup(markup);


        telegramFeign.sendMessage(sendMessage);
    }


    public void getFromCity(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(GET_FROM_TEXT);
        List<List<Region>> rows = new ArrayList<>();
        int i = 1;
        List<Region> row = new ArrayList<>();
        for (Region value : Region.values()) {
            row.add(value);
            if (i % 3 == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
            i++;
        }
        if (!row.isEmpty()) rows.add(row);
        editMessageText.setReplyMarkup(inlineKeyboard.createMarkupForRegion(FROM_REGION, rows, BACK_TO_MAIN_MENU));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        telegramFeign.editMessageText(editMessageText);
    }

    public void geToCity(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(GET_TO_TEXT);
        String data = update.getCallbackQuery().getData();
        List<List<Region>> rows = new ArrayList<>();
        String from = "null";
        if (data.startsWith(FROM_REGION))
            from = data.substring(FROM_REGION.length());
        else
            from = userService.getFromRegion(message.getChatId().toString());
        int i = 1;
        List<Region> row = new ArrayList<>();
        for (Region value : Region.values()) {
            if (value.getCode().equals(from)) continue;
            row.add(value);
            if (i % 3 == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
            i++;
        }
        if (!row.isEmpty()) rows.add(row);
        editMessageText.setReplyMarkup(inlineKeyboard.createMarkupForRegion(TO_REGION, rows, BACK_TO_GET_FROM_REGION));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        telegramFeign.editMessageText(editMessageText);

    }

    public void getDate(Update update, LocalDate date) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(GET_DATE_TEXT);
        editMessageText.setReplyMarkup(inlineKeyboard.createDate(date));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        telegramFeign.editMessageText(editMessageText);
    }

    public void showStations(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        User user = userService.findByChatId(message.getChatId().toString());
        SendMessage sendMessage;

        Station station = railwayFeign.getStation("en", new StationDTO(
                user.getFromRegion(), user.getToRegion(),
                List.of(new DirectionItem(true, DirectionType.FORWARD.getVal(), user.getDepartureDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))))
        ));
        if (station == null || station.getDirection() == null || station.getDirection().get(0).getTrains() == null) {
            getWarningSend(update, NOT_FOUND_TEXT, null);
            return;
        }
        List<SendMessage> sendMessageList = new ArrayList<>();
        for (var directionItem : station.getDirection())
            for (var trains : directionItem.getTrains())
                for (var train : trains.getTrain()) {
                    sendMessage = new SendMessage(message.getChatId().toString(), "<b>" +
                            train.getNumber() +
                            " (" +
                            train.getType() +
                            ")" +
                            "</b>   " +
                            train.getRoute().getStation().get(0) +
                            arrow +
                            train.getRoute().getStation().get(1) +
                            "\n" +
                            train.getDeparture().getLocalDate() +
                            arrow +
                            train.getArrival().getLocalDate() +
                            "\n<b>" +
                            train.getDeparture().getLocalTime() +
                            "</b>" +
                            arrow +
                            train.getTimeInWay() +
                            arrow +
                            "<b>" +
                            train.getArrival().getLocalTime() +
                            "</b>\n" +
                            directionItem.getPassRoute().getFrom() +
                            arrow +
                            directionItem.getPassRoute().getTo());
                    LinkedHashMap<CarsItem, TariffItem> data = new LinkedHashMap<>();
                    for (var car : train.getPlaces().getCars())
                        for (var tariff : car.getTariffs().getTariff())
                            data.put(car, tariff);
                    sendMessage.setReplyMarkup(inlineKeyboard.getStation(data, train.getNumber()));
                    sendMessage.setParseMode(ParseMode.HTML);
                    sendMessageList.add(sendMessage);
                }
        telegramFeign.deleteMessage(new DeleteMessage(message.getChatId().toString(), message.getMessageId()));
        for (int i = 0; i < sendMessageList.size(); i++) {
            if (i == sendMessageList.size() - 1) {
                SendMessage endMessage = sendMessageList.get(i);
                InlineKeyboardMarkup markup = (InlineKeyboardMarkup) endMessage.getReplyMarkup();
                markup.getKeyboard().add(inlineKeyboard.getRow(inlineKeyboard.getButton(MENU, MENU), inlineKeyboard.getButton(BACK_TO_GET_DATE, BACK)));
                endMessage.setReplyMarkup(markup);
                telegramFeign.sendMessage(endMessage);
            } else
                telegramFeign.sendMessage(sendMessageList.get(i));
        }


    }

    public void showTrain(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        User user = userService.findByChatId(message.getChatId().toString());
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), SHOW_TRAIN_TEXT);
        sendMessage.setParseMode(ParseMode.HTML);
        Place place = railwayFeign.getPlace("en", new PlaceDTO(1,
                user.getFromRegion(), user.getToRegion(),
                List.of(new uz.pdp.eticketrailway.payload.place.DirectionItem(true, DirectionType.FORWARD.getVal(),
                        user.getDepartureDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new Train(user.getTrainNumber())))
        ));
        sendMessage.setReplyMarkup(inlineKeyboard.getCars(place));
        sendMessage.setParseMode(ParseMode.HTML);
        telegramFeign.sendMessage(sendMessage);
    }

    public void showCar(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        User user = userService.findByChatId(message.getChatId().toString());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setParseMode(ParseMode.HTML);
        Place place = railwayFeign.getPlace("en", new PlaceDTO(1,
                user.getFromRegion(), user.getToRegion(),
                List.of(new uz.pdp.eticketrailway.payload.place.DirectionItem(true, DirectionType.FORWARD.getVal(),
                        user.getDepartureDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new Train(user.getTrainNumber())))
        ));
        boolean isNotFind = true;
        for (var directionItem : place.getDirection())
            if (isNotFind)
                for (var trains : directionItem.getTrains())
                    if (isNotFind)
                        for (var cars : trains.getTrain().getCars())
                            if (isNotFind)
                                for (var car : cars.getCar())
                                    if (car.getNumber().equals(user.getCarNumber())) {
                                        sendMessage.setReplyMarkup(inlineKeyboard.getCarSeats(cars, car));
                                        sendMessage.setText(
                                                WAGON_NUMBER + car.getNumber() + "\n" +
                                                        TYPE + cars.getType() + " " + cars.getClassService().getType() + "\n" +
                                                        PRICE + (cars.getComissionFee() + cars.getTariff()) + SUM + "\n" +
                                                        AVAILABLE_PLACES + (car.getSeats().getSeatsUndef() +
                                                        car.getSeats().getSeatsUp() + car.getSeats().getSeatsDn() +
                                                        car.getSeats().getSeatsLateralUp() + car.getSeats().getSeatsLateralDn()
                                                )
                                        );
                                        isNotFind = false;
                                        break;
                                    }
        telegramFeign.sendMessage(sendMessage);
    }

    public void getWarningSend(Update update, String text, String btn) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), text);
        if (btn != null)
            sendMessage.setReplyMarkup(inlineKeyboard.createMarkup(List.of(List.of(btn))));
        telegramFeign.sendMessage(sendMessage);
    }

    public void sendBunWarning(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), BUN_TEXT);
        InlineKeyboardButton btnSkip = new InlineKeyboardButton(GO);
        btnSkip.setCallbackData(SKIP);
        btnSkip.setUrl(Url.SUPPORT_BOT);
        sendMessage.setReplyMarkup(new InlineKeyboardMarkup(List.of(List.of(btnSkip))));
        telegramFeign.sendMessage(sendMessage);
    }

    public void getWarningEdit(Update update, String text) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(text);
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        telegramFeign.editMessageText(editMessageText);
    }
}
