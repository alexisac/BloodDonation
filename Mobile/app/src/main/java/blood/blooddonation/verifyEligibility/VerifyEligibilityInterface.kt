package blood.blooddonation.verifyEligibility

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Query

interface VerifyEligibilityInterface {
    @Headers("Content-Type: application/json")
    @GET("/verifyEligibility/getEligibilityTypeForUser")
    suspend fun getEligibilityTypeForUser(
        @Header("Authorization") authorization: String,
        @Query("idUser") idUser: String
    ): Int

    @Headers("Content-Type: application/json")
    @PUT("/verifyEligibility/saveEligibilityType")
    suspend fun saveEligibilityType(
        @Header("Authorization") authorization: String,
        @Query("idUser") idUser: String,
        @Query("eligibilityType") eligibilityType: Int
    )
}