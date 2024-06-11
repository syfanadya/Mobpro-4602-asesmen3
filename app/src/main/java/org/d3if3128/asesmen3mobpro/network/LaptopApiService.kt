package org.d3if3128.asesmen3mobpro.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "syfanadya/Static-JSON-3/main/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface LaptopApiService {
    @GET("static-api.json")
    suspend fun getLaptop(): String
}

object LaptopApi{
    val service: LaptopApiService by lazy {
        retrofit.create(LaptopApiService::class.java)
    }
}