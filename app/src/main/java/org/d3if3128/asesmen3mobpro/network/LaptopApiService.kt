package org.d3if3128.asesmen3mobpro.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3128.asesmen3mobpro.model.Laptop
import org.d3if3128.asesmen3mobpro.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface LaptopApiService {
    @GET("api_syfa.php")
    suspend fun getLaptop(
        @Header("Authorization") userId: String
    ): List<Laptop>

    @Multipart
    @POST("api_syfa.php")
    suspend fun postLaptop(
        @Header("Authorization") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("processor") processor: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus
}

object LaptopApi{
    val service: LaptopApiService by lazy {
        retrofit.create(LaptopApiService::class.java)
    }
    fun getLaptopUrl(imageId: String): String{
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus {LOADING, SUCCESS, FAILED}