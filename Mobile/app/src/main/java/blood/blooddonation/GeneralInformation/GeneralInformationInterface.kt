package blood.blooddonation.GeneralInformation

import blood.blooddonation.model.GeneralInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GeneralInformationInterface {
    @Headers("Content-Type: application/json")
    @POST("/user/saveGeneralInformation")
    suspend fun sendGeneralInformation(
        @Header("Authorization") authorization: String,
        @Body generalInfo: GeneralInfo
    ): Boolean

    @Headers("Content-Type: application/json")
    @GET("/user/getGeneralInformation")
    suspend fun getGeneralInformation(
        @Header("Authorization") authorization: String,
        @Query("idUser") idUser: String
    ): GeneralInfo
}