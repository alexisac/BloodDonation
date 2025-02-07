package blood.blooddonation.item

import blood.blooddonation.item.Item
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ItemService {
    @POST("/item")
    suspend fun find(
    ): List<Item>

    @GET("/api/item/{id}")
    suspend fun read(
        @Header("Authorization") authorization: String,
        @Path("id") itemId: String?
    ): Item

    @Headers("Content-Type: application/json")
    @POST("/api/item")
    suspend fun create(
        @Header("Authorization") authorization: String,
        @Body item: Item
    ): Item

    @Headers("Content-Type: application/json")
    @PUT("/api/item/{id}")
    suspend fun update(
        @Header("Authorization") authorization: String,
        @Path("id") itemId: String?,
        @Body item: Item
    ): Item
}