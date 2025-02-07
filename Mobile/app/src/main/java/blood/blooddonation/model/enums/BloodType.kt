package blood.blooddonation.model.enums

enum class BloodType(val displayName: String, val intValue: Int) {
    ZEROPOSITIVE("0+", 0),
    ZERONEGATIVE("0-", 1),
    APOSITIVE("A+", 2),
    ANEGATIVE("A-", 3),
    BPOSITIVE("B+", 4),
    BNEGATIVE("B-", 5),
    ABPOSITIVE("AB+", 6),
    ABNEGATIVE("AB-", 7),
    UNDEFINED("undefined", 8);
}

fun convertIntToBloodType(value : Int): BloodType {
    return if(value == BloodType.ZEROPOSITIVE.intValue) BloodType.ZEROPOSITIVE;
    else if(value == BloodType.ZERONEGATIVE.intValue) BloodType.ZERONEGATIVE;
    else if(value == BloodType.APOSITIVE.intValue) BloodType.APOSITIVE;
    else if(value == BloodType.ANEGATIVE.intValue) BloodType.ANEGATIVE;
    else if(value == BloodType.BPOSITIVE.intValue) BloodType.BPOSITIVE;
    else if(value == BloodType.BNEGATIVE.intValue) BloodType.BNEGATIVE;
    else if(value == BloodType.ABPOSITIVE.intValue) BloodType.ABPOSITIVE;
    else if(value == BloodType.ABNEGATIVE.intValue) BloodType.ABNEGATIVE;
    else BloodType.UNDEFINED;
}