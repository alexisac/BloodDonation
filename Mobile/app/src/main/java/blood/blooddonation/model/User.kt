package blood.blooddonation.model

data class User (
    var id: String,
    var email: String,
    var encryptedCNP: String,
    var encryptedPassword: String
)