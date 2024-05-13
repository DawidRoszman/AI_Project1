package eu.dawidroszman.foodlicious

import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

val gson = GsonBuilder()
    .setLenient()
    .create()
private const val BASE_URL = "https://foodlicious.dawidroszman.eu"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()
interface PhotoApiService{
    @Multipart
    @POST("get_fruit_data")
    suspend fun getFruitData(@Part file: MultipartBody.Part): FruitData
}

object PhotoApi {
    val retrofitService : PhotoApiService by lazy {
        retrofit.create(PhotoApiService::class.java)
    }
}