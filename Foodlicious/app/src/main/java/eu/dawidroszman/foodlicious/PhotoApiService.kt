package eu.dawidroszman.foodlicious

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http://0.0.0.0:8080"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
interface PhotoApiService{
    @GET("hello")
    suspend fun getHello(): String
    @POST("get_fruit_data")
    suspend fun getFruiData(): FruitData
}

object PhotoApi {
    val retrofitService : PhotoApiService by lazy {
        retrofit.create(PhotoApiService::class.java)
    }
}