package blood.blooddonation.preferences

data class UserPreferences(
    val id: String = "",
    val token: String = "",
    val finish: Boolean = false
)