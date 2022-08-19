package uz.pdp.eticketrailway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.eticketrailway.feign.RailwayFeign;
import uz.pdp.eticketrailway.model.place.CarItem;
import uz.pdp.eticketrailway.model.place.DirectionItem;
import uz.pdp.eticketrailway.model.place.Place;
import uz.pdp.eticketrailway.model.place.Seats;
import uz.pdp.eticketrailway.model.railway.CarsItem;
import uz.pdp.eticketrailway.model.railway.TariffItem;
import uz.pdp.eticketrailway.utils.enums.CarType;
import uz.pdp.eticketrailway.utils.enums.Region;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static uz.pdp.eticketrailway.utils.interfaces.Constant.*;

@Service
@RequiredArgsConstructor
public class InlineKeyboardService {

    private final RailwayFeign railwayFeign;


    public InlineKeyboardMarkup createMarkup(List<List<String>> rows) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (List<String> row : rows) {
            List<InlineKeyboardButton> dRow = new ArrayList<>();
            for (String word : row) {
                InlineKeyboardButton button;
                if (!word.contains("BACK"))
                    button = getButton(word, word);
                else
                    button = getButton(word, BACK);
                dRow.add(button);
            }
            rowList.add(dRow);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createMarkupForRegion(String prefix, List<List<Region>> rows, String back) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (List<Region> row : rows) {
            List<InlineKeyboardButton> dRow = new ArrayList<>();
            for (Region word : row)
                dRow.add(getButton(prefix + word.getCode(), word.name()));
            rowList.add(dRow);
        }
        rowList.add(Collections.singletonList(getButton(back, BACK)));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup createDate(LocalDate date) {
        String dateStr = date.toString();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(Collections.singletonList(getButton(SKIP, date.getMonth().name() + " " + date.getYear())));
        List<InlineKeyboardButton> weeks = new ArrayList<>();
        for (String week : Arrays.asList("M", "T", "W", "R", "F", "S", "U"))
            weeks.add(getButton(SKIP, week));
        rowList.add(weeks);

        int monthValue = date.getMonthValue();
        date = date.minusDays(date.getDayOfMonth() - 1);
        Map<String, String> days = new LinkedHashMap<>();
        while (date.getMonthValue() == monthValue) {
            for (int i = 1; i <= 7; i++) {
                if (i == date.getDayOfWeek().getValue() && date.getMonthValue() == monthValue) {
                    days.put(DATE + date, String.valueOf(date.getDayOfMonth()));
                    date = date.plusDays(1);
                } else
                    days.put(SKIP + i, " ");
            }
            rowList.add(getRow(days));
            days = new LinkedHashMap<>();
        }
        LinkedHashMap<String, String> floor = new LinkedHashMap<>();
        floor.put(PREV + dateStr, PREV);
        floor.put(BACK_TO_GET_TO_REGION, BACK);
        floor.put(NEXT + dateStr, NEXT);
        rowList.add(getRow(floor));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getStation(LinkedHashMap<CarsItem, TariffItem> data, String callBackData) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (CarsItem car : data.keySet()) {
            TariffItem tariff = data.get(car);
            rowList.add(getRow(getButton(TRAIN + callBackData, "\uD83D\uDE88 " + car.getTypeShow() +
                    "    \uD83D\uDCBA " + car.getFreeSeats() + "    \uD83D\uDCB0 " +
                    (tariff.getTariff() + tariff.getComissionFee())
            )));
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup getCars(Place place) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        int cnt = 0;
        for (DirectionItem directionItem : place.getDirection())
            for (var trains : directionItem.getTrains())
                for (var cars : trains.getTrain().getCars())
                    for (var car : cars.getCar()) {
                        Seats seats = car.getSeats();
                        int freeSeatNum = seats.getSeatsDn() + seats.getSeatsUp() + seats.getSeatsUndef() +
                                seats.getSeatsLateralDn() + seats.getSeatsLateralUp();
                        if (cnt < 2) {
                            buttons.add(getButton(CAR + car.getNumber(),
                                    car.getNumber() + "\uD83D\uDE86   " + freeSeatNum + "\uD83D\uDCBA"));
                        } else {
                            rows.add(buttons);
                            cnt = -1;
                            buttons = new ArrayList<>();
                        }
                        cnt++;
                    }
        if (!buttons.isEmpty())
            rows.add(buttons);

        rows.add(getRow(getButton(DELETE, BACK)));
        return new InlineKeyboardMarkup(rows);
    }


    public InlineKeyboardMarkup getCarSeats(uz.pdp.eticketrailway.model.place.CarsItem cars, CarItem car) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        List<Integer> freeSeats = Arrays.stream(Arrays.stream(car.getPlaces().split(",")).mapToInt(Integer::parseInt).toArray()).boxed().collect(Collectors.toList());
        int len = car.getTypeOfCarSchema() == null ? 0 : car.getTypeOfCarSchema().getSeatsCount();
            for (CarType carType : CarType.values())
                if (carType.getType().equals(cars.getType()) && carType.getClassServiceType().equals(cars.getClassService().getType())) {
                    len = Math.max(carType.getNumberSeats(), len);
                    break;
                }
        for (int i = 1; i <= len; i++) {
            buttons.add(getButton(SKIP, i + (freeSeats.contains(i) ? RIGHT : WRONG)));
            if (i % 5 == 0) {
                rows.add(buttons);
                buttons = new ArrayList<>();
            }
        }
        if (!buttons.isEmpty())
            rows.add(buttons);
        rows.add(getRow(getButton(DELETE, BACK)));
        return new InlineKeyboardMarkup(rows);
    }


    public InlineKeyboardButton getButton(String callBackData, String text) {
        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.setCallbackData(callBackData);
        return button;
    }

    public List<InlineKeyboardButton> getRow(Map<String, String> buttons) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (String data : buttons.keySet())
            row.add(getButton(data, buttons.get(data)));
        return row;
    }

    public List<InlineKeyboardButton> getRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(Arrays.asList(buttons));
    }


}
