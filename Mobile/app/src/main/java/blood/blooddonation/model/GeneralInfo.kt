package blood.blooddonation.model

data class GeneralInfo (
    var idUser: String,
    var firstName: String,
    var lastName: String,
    var birthDate: String,
    var sex: String,
    var bloodType: String,
    var points: Int,
)