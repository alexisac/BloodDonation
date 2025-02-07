package blood.blooddonation.model

import blood.blooddonation.model.dtos.DonationHistoryEntryDto
import blood.blooddonation.model.enums.DonationType

data class DonationHistoryEntry (
    var idUser: String,
    var donationDate: String,
    var donationType: DonationType,
)

fun convertDtoToEntry(dto: DonationHistoryEntryDto): DonationHistoryEntry {
    return DonationHistoryEntry(
        idUser = dto.idUser,
        donationDate = dto.donationDate,
        donationType = DonationType.fromDisplayName(dto.donationType)
    )
}