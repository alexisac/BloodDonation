package blood.blooddonation.service

import blood.blooddonation.model.DonationCenter
import blood.blooddonation.repository.DonationCentersRepository

class DonationCentersService(
    private val donationCentersRepository: DonationCentersRepository
) {
    suspend fun getAllDonationCenters(): Result<List<DonationCenter>> {
        return donationCentersRepository.getAllDonationCenters()
    }

    suspend fun getBookedDates(date: String, centerId: String): Result<List<String>> {
        return donationCentersRepository.getBookedDates(date, centerId)
    }

    suspend fun saveScheduleDate(idUser: String, date: String, centerId: String) {
        donationCentersRepository.saveScheduleDate(idUser, date, centerId)
    }
}