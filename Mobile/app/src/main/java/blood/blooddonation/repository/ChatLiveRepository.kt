package blood.blooddonation.repository

import blood.blooddonation.chatLive.ChatLiveInterface
import blood.blooddonation.utils.Api
import retrofit2.HttpException

class ChatLiveRepository {
    private val chatLiveInterface: ChatLiveInterface =
        Api.retrofit.create(ChatLiveInterface::class.java)

    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun getChatBotAnswer(question: String): Result<String> {
        return try {
            val result = chatLiveInterface.getChatBotAnswer(
                authorization = getBearerToken(),
                question = question
            )
            Result.success(result.text)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(Exception(errorBody ?: "Eroare 500. Probleme la server"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}