package blood.blooddonation.model.enums

enum class Sex(val displayName: String, val intValue: Int) {
    UNDEFINED("undefined", 0),
    FEMININ("feminin", 1),
    MASCULIN("masculin", 2);
}

fun convertIntToGenderType(value: Int): Sex {
    return if (value == Sex.UNDEFINED.intValue) Sex.UNDEFINED
    else if (value == Sex.MASCULIN.intValue) Sex.MASCULIN
    else Sex.FEMININ
}