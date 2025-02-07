package blood.blooddonation.donationHistory

import blood.blooddonation.model.dtos.DonationHistoryEntryDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface DonationHistoryInterface {
    @Headers("Content-Type: application/json")
    @GET("/donationHistory/getAll")
    suspend fun getAllDonationHistory(
        @Header("Authorization") authorization: String,
        @Query("idUser") idUser: String
    ): List<DonationHistoryEntryDto>
}