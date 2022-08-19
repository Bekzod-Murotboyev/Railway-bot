package uz.pdp.eticketrailway.utils.enums;

public enum CarType {
    SITTING_2E("Сидячий","2E",36),

    SITTING_1C("Сидячий","1С",28),

    SITTING_1B("Сидячий","1B",15),
    SITTING_2B("Сидячий","2B",47),

    CLASS_1("Люкс","1Л",20),
    CLASS_2("Купе","2К",36),
    CLASS_3("Плацкартный","3П",54),
    CLASS_4("Общий","3О",81);

    private final String type;
    private final String classServiceType;
    private final int numberSeats;

    CarType(String type, String classServiceType, int numberSeats) {
        this.type = type;
        this.classServiceType = classServiceType;
        this.numberSeats = numberSeats;
    }


    public String getType() {
        return type;
    }

    public String getClassServiceType() {
        return classServiceType;
    }

    public int getNumberSeats() {
        return numberSeats;
    }
}
