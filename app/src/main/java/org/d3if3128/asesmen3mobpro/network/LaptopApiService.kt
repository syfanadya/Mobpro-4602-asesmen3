package org.d3if3128.asesmen3mobpro.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3128.asesmen3mobpro.model.Laptop
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "syfanadya/Static-JSON-3/main/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface LaptopApiService {
    @GET("static-api.json")
    suspend fun getLaptop(): List<Laptop>
}

object LaptopApi{
    val service: LaptopApiService by lazy {
        retrofit.create(LaptopApiService::class.java)
    }
    fun getLaptopUrl(imageId: String): String{
        return "$BASE_URL$imageId.jpg"
    }
}