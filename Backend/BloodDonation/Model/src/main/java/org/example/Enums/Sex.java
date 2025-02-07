package org.example.Enums;

import java.sql.PreparedStatement;
import java.util.Objects;

public enum Sex {
    UNDEFINED("undefined", 0),
    FEMININ("feminin", 1),
    MASCULIN("masculin", 2);

    private final String name;
    private final int intValue;


    // constructor
    Sex(String name, int intValue) {
        this.name = name;
        this.intValue = intValue;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getIntValue() {
        return intValue;
    }

    public static Sex getObject(String name) {
        if (Objects.equals(name, Sex.UNDEFINED.getName())) return Sex.UNDEFINED;
        else if(Objects.equals(name, Sex.MASCULIN.getName())) return Sex.MASCULIN;
        else return Sex.FEMININ;
    }

    public static Sex getObjectByValue(int value) {
        if (value == Sex.UNDEFINED.intValue) return Sex.UNDEFINED;
        else if(value == Sex.MASCULIN.intValue) return Sex.MASCULIN;
        else return Sex.FEMININ;
    }
}
