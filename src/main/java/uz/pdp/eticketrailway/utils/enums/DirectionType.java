package uz.pdp.eticketrailway.utils.enums;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum DirectionType {
    FORWARD("Forward"),
    BACK_WORD("Backward");

    String val;
    DirectionType(String val) {
        this.val=val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
