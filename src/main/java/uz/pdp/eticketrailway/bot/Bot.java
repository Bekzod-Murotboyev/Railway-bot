package uz.pdp.eticketrailway.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.eticketrailway.payload.UserInfoDTO;
import uz.pdp.eticketrailway.service.BotService;
import uz.pdp.eticketrailway.utils.enums.BotState;
import uz.pdp.eticketrailway.utils.interfaces.Constant;

import java.time.LocalDate;

import static uz.pdp.eticketrailway.utils.enums.BotState.*;
import static uz.pdp.eticketrailway.utils.interfaces.Constant.*;
import static uz.pdp.eticketrailway.utils.interfaces.Security.TOKEN;
import static uz.pdp.eticketrailway.utils.interfaces.Security.USERNAME;

@Service
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final BotService botService;


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            if (data.equals(Constant.SKIP)) return;
            if (data.equals(Constant.DELETE)) {
                botService.deleteMessage(update);
                return;
            }
            if (data.startsWith(Constant.PREV) || data.startsWith(Constant.NEXT)) {
                botService.switchDate(update);
                return;
            }
        }
        try {
            makeAction(update);
        } catch (Exception e) {
            clearWebhook();
        }
    }

    public void makeAction(Update update) {
        UserInfoDTO userInfoDTO = botService.getAndCheck(update);
        if (!userInfoDTO.isActive()) {
            botService.sendBunWarning(update);
            return;
        }
        BotState state = userInfoDTO.getState();
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals(COMMAND_START))
                    state = BotState.MAIN_MENU_SEND;
                else if (text.equals(BACK) && state.equals(MENU_REGISTER))
                    state = SETTINGS_MENU_SEND;
                else
                    state = MAIN_MENU_SEND;
            } else if (message.hasContact()) {
                state = SETTINGS_MENU_SEND;
            }
        } else if (update.hasCallbackQuery()) {
            switch (update.getCallbackQuery().getData()) {
                case SETTINGS:
                    state = SETTINGS_MENU_EDIT;
                    break;
                case REGISTER:
                    state = MENU_REGISTER;
                    break;
                case BACK_TO_GET_FROM_REGION:
                case SEARCH:
                    state = GET_FROM;
                    break;
                case BACK_TO_MAIN_MENU:
                    state = MAIN_MENU_EDIT;
                    break;
                case BACK_TO_GET_TO_REGION:
                    state = GET_TO;
                    break;
                case BACK_TO_GET_DATE:
                    state = GET_DATE;
                    break;
                default:
                    state = botService.getState(update);
            }
            ;
        }
        if (state.equals(SKIP_ACTION)) return;
        switch (state) {
            case SEND_WARNING : botService.getWarningSend(update, REGISTER_WARNING_TEXT, SETTINGS);break;
            case MAIN_MENU_SEND : botService.getMainMenuSend(update);break;
            case MAIN_MENU_EDIT : botService.getMainMenuEdit(update);break;
            case SETTINGS_MENU_SEND : {
                botService.removeKeyBoardMarkup(update);
                botService.getSettingsMenuSend(update);
                break;
            }
            case SETTINGS_MENU_EDIT : botService.getSettingsMenuEdit(update);break;
            case MENU_REGISTER : {
                botService.deleteMessage(update);
                botService.getMenuRegister(update);
                break;
            }
            case GET_FROM : botService.getFromCity(update);break;
            case GET_TO : botService.geToCity(update);break;
            case GET_DATE : botService.getDate(update, LocalDate.now());break;
            case SHOW_STATIONS : botService.showStations(update);break;
            case SHOW_TRAIN : botService.showTrain(update);break;
            case SHOW_CAR : botService.showCar(update);break;
        }

        botService.saveUserData(update, state);
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
