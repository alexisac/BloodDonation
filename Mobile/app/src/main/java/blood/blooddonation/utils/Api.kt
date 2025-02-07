package blood.blooddonation.utils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private val URL = "1.1.1.1:8080"
    private val HTTP_URL = "http://$URL/"
    val webSoketUrl = "ws://$URL"

    private var gson = GsonBuilder()
        .create()

    val tokenInterceptor = TokenInterceptor()

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(tokenInterceptor)
        this.addInterceptor(tokenInterceptor)
    }.build()

    val retrofit = Retrofit.Builder()
        .baseUrl(HTTP_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}