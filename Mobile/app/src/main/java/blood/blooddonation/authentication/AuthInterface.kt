package blood.blooddonation.authentication
import blood.blooddonation.model.SimpleUser
import blood.blooddonation.model.Token
import blood.blooddonation.model.User
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthInterface {
    @Headers("Content-Type: application/json")
    @POST("/user/login")
    suspend fun login(
        @Body user: SimpleUser
    ): Token

    @Headers("Content-Type: application/json")
    @POST("/user/register")
    suspend fun register(
        @Body user: User
    ): Token

    @Headers("Content-Type: application/json")
    @POST("/user/resetPassword")
    suspend fun resetPassword(
        @Body user: User
    ): Boolean
}