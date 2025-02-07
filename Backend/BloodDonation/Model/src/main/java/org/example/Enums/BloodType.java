package org.example.Enums;

import java.util.Objects;

public enum BloodType {
    ZEROPOSITIVE("0+", 0),
    ZERONEGATIVE("0-", 1),
    APOSITIVE("A+", 2),
    ANEGATIVE("A-", 3),
    BPOSITIVE("B+", 4),
    BNEGATIVE("B-", 5),
    ABPOSITIVE("AB+", 6),
    ABNEGATIVE("AB-", 7),
    UNDEFINED("undefined", 8);

    private final String name;
    private final int intValue;

    // constructor
    BloodType(String name, int intValue) {
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

    public static BloodType getObject(String name) {
        if(Objects.equals(name, BloodType.ZEROPOSITIVE.getName())) return BloodType.ZEROPOSITIVE;
        else if(Objects.equals(name, BloodType.ZERONEGATIVE.getName())) return BloodType.ZERONEGATIVE;
        else if(Objects.equals(name, BloodType.APOSITIVE.getName())) return BloodType.APOSITIVE;
        else if(Objects.equals(name, BloodType.ANEGATIVE.getName())) return BloodType.ANEGATIVE;
        else if(Objects.equals(name, BloodType.BPOSITIVE.getName())) return BloodType.BPOSITIVE;
        else if(Objects.equals(name, BloodType.BNEGATIVE.getName())) return BloodType.BNEGATIVE;
        else if(Objects.equals(name, BloodType.ABPOSITIVE.getName())) return BloodType.ABPOSITIVE;
        else if(Objects.equals(name, BloodType.ABNEGATIVE.getName())) return BloodType.ABNEGATIVE;
        else return BloodType.UNDEFINED;
    }

    public static BloodType getObjectByValue(int value) {
        if(value == BloodType.ZEROPOSITIVE.intValue) return BloodType.ZEROPOSITIVE;
        else if(value == BloodType.ZERONEGATIVE.intValue) return BloodType.ZERONEGATIVE;
        else if(value == BloodType.APOSITIVE.intValue) return BloodType.APOSITIVE;
        else if(value == BloodType.ANEGATIVE.intValue) return BloodType.ANEGATIVE;
        else if(value == BloodType.BPOSITIVE.intValue) return BloodType.BPOSITIVE;
        else if(value == BloodType.BNEGATIVE.intValue) return BloodType.BNEGATIVE;
        else if(value == BloodType.ABPOSITIVE.intValue) return BloodType.ABPOSITIVE;
        else if(value == BloodType.ABNEGATIVE.intValue) return BloodType.ABNEGATIVE;
        else return BloodType.UNDEFINED;
    }
}
