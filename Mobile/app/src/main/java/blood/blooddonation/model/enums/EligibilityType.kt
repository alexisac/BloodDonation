package blood.blooddonation.model.enums

enum class EligibilityType(val displayName: String, val intValue: Int) {
    ELIGIBIL("Eligibil", 0),
    ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION(
        "Eligibil dar se recomandă o evaluare suplimentară", 1),
    NOT_ELIGIBIL("Nu este eligibil", 2),
    UNDEFINED("Nedeterminat", 3);
}

fun convertIntToEntity(value: Int): EligibilityType {
    return if(value == EligibilityType.ELIGIBIL.intValue) EligibilityType.ELIGIBIL
    else if(value == EligibilityType.NOT_ELIGIBIL.intValue) EligibilityType.NOT_ELIGIBIL
    else if(value == EligibilityType.ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION.intValue) EligibilityType.ELIGIBIL_BUT_NEED_MEDICAL_CONSULTATION
    else EligibilityType.UNDEFINED
}