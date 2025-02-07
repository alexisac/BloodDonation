package blood.blooddonation.service

import blood.blooddonation.model.SimpleUser
import blood.blooddonation.model.Token
import blood.blooddonation.model.User
import blood.blooddonation.repository.AuthRepository
import blood.blooddonation.utils.Api

class AuthService(private val authRepository: AuthRepository) {
    suspend fun login(
        email: String,
        encryptedPassword: String
    ): Result<Token> {
        val user = SimpleUser(email, encryptedPassword)
        val result = authRepository.login(user)

        if (result.isSuccess) {
            Api.tokenInterceptor.token = result.getOrNull()?.token
        }

        return result
    }

    suspend fun register(
        id: String,
        email: String,
        encryptedCNP: String,
        encryptedPassword: String
    ): Result<Token> {
        val user = User(id, email, encryptedCNP, encryptedPassword)
        val result = authRepository.register(user)

        if(result.isSuccess) {
            Api.tokenInterceptor.token = result.getOrNull()?.token
        }

        return result
    }

    suspend fun resetPassword(
        id: String,
        email: String,
        encryptedCNP: String,
        encryptedPassword: String
    ): Result<Boolean> {
        val user = User(id, email, encryptedCNP, encryptedPassword)
        return authRepository.resetPassword(user)
    }
}