package blood.blooddonation.model.dtos

data class DonationHistoryEntryDto (
    var idUser: String,
    var donationDate: String,
    var donationType: String,
)