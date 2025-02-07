package blood.blooddonation.repository

import blood.blooddonation.GeneralInformation.GeneralInformationInterface
import blood.blooddonation.model.GeneralInfo
import blood.blooddonation.model.ScheduleCenter
import blood.blooddonation.schedules.SchedulesInterface
import blood.blooddonation.utils.Api
import retrofit2.HttpException

class ScheduleRepository {
    private val scheduleInterface: SchedulesInterface =
        Api.retrofit.create(SchedulesInterface::class.java)
    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun getBookedForUser(idUser: String): Result<List<ScheduleCenter>> {
        return try{
            Result.success(scheduleInterface.getBookedForUser(
                authorization = getBearerToken(),
                idUser = idUser
            ))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}