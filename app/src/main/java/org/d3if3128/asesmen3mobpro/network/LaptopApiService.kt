package org.d3if3128.asesmen3mobpro.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3128.asesmen3mobpro.model.Laptop
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

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
    suspend fun getLaptop(): List<Laptop>
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