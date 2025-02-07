package blood.blooddonation.service

import blood.blooddonation.model.DonationHistoryEntry
import blood.blooddonation.repository.DonationHistoryRepository

class DonationHistoryService (
    private val donationHistoryRepository: DonationHistoryRepository
) {
    suspend fun getAllDonationHistory(idUser: String): Result<List<DonationHistoryEntry>> {
        return donationHistoryRepository.getAllDonationHistory(idUser = idUser)
    }
}