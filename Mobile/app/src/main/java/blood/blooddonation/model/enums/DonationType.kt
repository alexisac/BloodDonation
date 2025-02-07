package blood.blooddonation.model.enums;

enum class DonationType(val displayName: String, val intValue: Int) {
    UNDEFINED("undefined", 0),
    WHOLE_BLOOD("whole blood", 1),
    PLASMA("plasma", 2),
    PLATELETS("platelets", 3); // trombocite

    companion object {
        fun fromDisplayName(displayName: String): DonationType {
            return values().find { it.displayName.equals(displayName, ignoreCase = true) } ?: UNDEFINED
        }
    }
}
