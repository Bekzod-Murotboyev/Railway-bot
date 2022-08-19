package uz.pdp.eticketrailway.utils.enums;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Region {
    Tashkent("2900000"),
    Nukus("2900970"),
    Samarkand("2900700"),
    Khiva("2900172"),
    Bukhara("2900800"),
    Andijan("2900680"),
    Qarshi("2900750"),
    Navai("2900930"),
    Djizak("2900720"),
    Termez("2900255"),
    Gulistan("2900850"),
    Urgench("2900790");

    String code;
    Region(String code) {
        this.code=code;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
