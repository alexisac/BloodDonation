package org.example.Enums;

import java.util.Objects;

public enum DonationType {
    UNDEFINED("undefined", 0),
    WHOLE_BLOOD("whole blood", 1),
    PLASMA("plasma", 2),
    PLATELETS("platelets", 3);  // trombocite

    private final String name;
    private final int intValue;

    DonationType(String name, int intValue) {
        this.name = name;
        this.intValue = intValue;
    }

    public String getName() {
        return name;
    }

    public int getIntValue() {
        return intValue;
    }

    public static DonationType getObject(String name) {
        if(Objects.equals(name, DonationType.UNDEFINED.getName())) return DonationType.UNDEFINED;
        else if(Objects.equals(name, DonationType.WHOLE_BLOOD.getName())) return DonationType.WHOLE_BLOOD;
        else if(Objects.equals(name, DonationType.PLASMA.getName())) return DonationType.PLASMA;
        else return DonationType.PLATELETS;
    }

    public static DonationType getObjectByValue(int value) {
        if(value == DonationType.UNDEFINED.getIntValue()) return DonationType.UNDEFINED;
        else if(value == DonationType.WHOLE_BLOOD.getIntValue()) return DonationType.WHOLE_BLOOD;
        else if(value == DonationType.PLASMA.getIntValue()) return DonationType.PLASMA;
        else return DonationType.PLATELETS;
    }
}
