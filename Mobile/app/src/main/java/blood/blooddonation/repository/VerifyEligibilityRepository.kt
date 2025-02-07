package blood.blooddonation.repository

import blood.blooddonation.model.enums.EligibilityType
import blood.blooddonation.model.enums.convertIntToEntity
import blood.blooddonation.utils.Api
import blood.blooddonation.verifyEligibility.VerifyEligibilityInterface
import retrofit2.HttpException

class VerifyEligibilityRepository {
    private val verifyEligibilityInterface: VerifyEligibilityInterface =
        Api.retrofit.create(VerifyEligibilityInterface::class.java)

    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun getEligibilityTypeForUser(idUser: String): Result<EligibilityType> {
        return try{
            val result = verifyEligibilityInterface.getEligibilityTypeForUser(
                authorization = getBearerToken(),
                idUser = idUser
            )
            Result.success(convertIntToEntity(result))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveEligibilityType(idUser: String, eligibilityType: Int) {
        try{
            verifyEligibilityInterface.saveEligibilityType(
                authorization = getBearerToken(),
                idUser = idUser,
                eligibilityType = eligibilityType
            )
        } catch (_: Exception) { }
    }
}