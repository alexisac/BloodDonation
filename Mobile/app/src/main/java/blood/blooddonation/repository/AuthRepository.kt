package blood.blooddonation.repository

import blood.blooddonation.authentication.AuthInterface
import blood.blooddonation.model.SimpleUser
import blood.blooddonation.model.Token
import blood.blooddonation.model.User
import blood.blooddonation.utils.Api
import retrofit2.HttpException
import kotlin.Exception

class AuthRepository {
    private val authInterface: AuthInterface = Api.retrofit.create(AuthInterface::class.java)

    suspend fun login(user: SimpleUser): Result<Token> {
        return try {
            Result.success(authInterface.login(user))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(user: User): Result<Token> {
        return try {
            Result.success(authInterface.register(user))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(user: User): Result<Boolean> {
        return try {
            Result.success(authInterface.resetPassword(user))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}