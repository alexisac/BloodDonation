package blood.blooddonation.repository

import blood.blooddonation.donationCenter.DonationCenterInterface
import blood.blooddonation.model.DonationCenter
import blood.blooddonation.utils.Api
import retrofit2.HttpException

class DonationCentersRepository {
    private val donationCenterInterface: DonationCenterInterface =
        Api.retrofit.create(DonationCenterInterface::class.java)
    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun getAllDonationCenters(): Result<List<DonationCenter>> {
        return try{
            Result.success(donationCenterInterface.getAllDonationCenters(
                authorization = getBearerToken()
            ))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getBookedDates(date: String, centerId: String): Result<List<String>> {
        return try{
            Result.success(donationCenterInterface.getBookedDates(
                authorization = getBearerToken(),
                date = date,
                centerId = centerId
            ))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveScheduleDate(idUser: String, date: String, centerId: String) {
        try{
            donationCenterInterface.saveScheduleDate(
                authorization = getBearerToken(),
                idUser = idUser,
                date = date,
                centerId = centerId
            )
        } catch (_: Exception) { }
    }
}