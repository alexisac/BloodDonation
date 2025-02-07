package blood.blooddonation.donationCenter

import blood.blooddonation.model.DonationCenter
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface DonationCenterInterface {
    @Headers("Content-Type: application/json")
    @GET("/donationCenter/getAll")
    suspend fun getAllDonationCenters(
        @Header("Authorization") authorization: String
    ): List<DonationCenter>


    @Headers("Content-Type: application/json")
    @POST("/bookedCenters/getBookedDates")
    suspend fun getBookedDates(
        @Header("Authorization") authorization: String,
        @Query("date") date: String,
        @Query("centerId") centerId: String
    ): List<String>


    @Headers("Content-Type: application/json")
    @PUT("/bookedCenters/bookDate")
    suspend fun saveScheduleDate(
        @Header("Authorization") authorization: String,
        @Query("idUser") idUser: String,
        @Query("date") date: String,
        @Query("centerId") centerId: String,
    )
}