package blood.blooddonation.repository;

import blood.blooddonation.GeneralInformation.GeneralInformationInterface
import blood.blooddonation.model.GeneralInfo
import blood.blooddonation.utils.Api
import retrofit2.HttpException

class GeneralInfoRepository {
    private val generalInfoInterface: GeneralInformationInterface =
        Api.retrofit.create(GeneralInformationInterface::class.java)
    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun sendGeneralInfo(generalInfo: GeneralInfo): Result<Boolean> {
        return try {
            Result.success(
                generalInfoInterface.sendGeneralInformation(
                    authorization = getBearerToken(),
                    generalInfo = generalInfo
                )
            )
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getGeneralInformation(idUser: String): Result<GeneralInfo> {
        return try{
            Result.success(generalInfoInterface.getGeneralInformation(
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
