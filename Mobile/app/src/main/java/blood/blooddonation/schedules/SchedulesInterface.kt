package blood.blooddonation.schedules

import blood.blooddonation.model.GeneralInfo
import blood.blooddonation.model.ScheduleCenter
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface SchedulesInterface {
    @Headers("Content-Type: application/json")
    @GET("/bookedCenters/getBookedForUser")
    suspend fun getBookedForUser(
        @Header("Authorization") authorization: String,
        @Query("idUser") idUser: String
    ): List<ScheduleCenter>
}