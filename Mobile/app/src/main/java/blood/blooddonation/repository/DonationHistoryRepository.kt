package blood.blooddonation.repository;

import blood.blooddonation.donationHistory.DonationHistoryInterface
import blood.blooddonation.model.DonationHistoryEntry
import blood.blooddonation.model.convertDtoToEntry
import blood.blooddonation.utils.Api
import retrofit2.HttpException

class DonationHistoryRepository {
    private val donationHistoryInterface: DonationHistoryInterface =
        Api.retrofit.create(DonationHistoryInterface::class.java)

    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun getAllDonationHistory(idUser: String): Result<List<DonationHistoryEntry>> {
        return try {
            val result = donationHistoryInterface.getAllDonationHistory(
                authorization = getBearerToken(),
                idUser = idUser
            )
            val dtoList = Result.success(result)
            dtoList.mapCatching { dtoList ->
                dtoList.map { dto ->
                    convertDtoToEntry(dto)
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
