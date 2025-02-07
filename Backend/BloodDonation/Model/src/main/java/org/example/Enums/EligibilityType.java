package org.example.Enums;

import java.util.Objects;

public enum EligibilityType {
    ELIGIBIL("Eligibil", 0),
    ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION(
            "Eligibil dar se recomanda o evaluare suplimentara", 1),
    NOT_ELIGIBIL("Nu este eligibil", 2),
    UNDEFINED("undefined", 3);

    private final String name;
    private final int intValue;

    EligibilityType(String name, int intValue) {
        this.name = name;
        this.intValue = intValue;
    }

    public String getName() {
        return name;
    }

    public int getIntValue() {
        return intValue;
    }

    public static EligibilityType getObject(String name) {
        if(Objects.equals(name, EligibilityType.ELIGIBIL.getName())) return EligibilityType.ELIGIBIL;
        else if(Objects.equals(name, EligibilityType.ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION.getName()))
            return EligibilityType.ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION;
        else if(Objects.equals(name, EligibilityType.NOT_ELIGIBIL.getName())) return EligibilityType.NOT_ELIGIBIL;
        else return EligibilityType.UNDEFINED;
    }

    public static EligibilityType getObjectByValue(int value) {
        if(value == EligibilityType.ELIGIBIL.getIntValue()) return EligibilityType.ELIGIBIL;
        else if(value == EligibilityType.ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION.getIntValue())
            return EligibilityType.ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION;
        else if(value == EligibilityType.NOT_ELIGIBIL.getIntValue()) return EligibilityType.NOT_ELIGIBIL;
        else return EligibilityType.UNDEFINED;
    }
}
