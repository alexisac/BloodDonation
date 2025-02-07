package blood.blooddonation.utils

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor{
    var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalCall = chain.request()

        val originalURL = originalCall.url

        if(token == null) return chain.proceed(originalCall)

        val requestBuilder = originalCall.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .url(originalURL)

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}