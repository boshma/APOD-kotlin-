package com.example.apodapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiService {
    @GET("planetary/apod")
    suspend fun getApod(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = "ZRjoBQ90NmtxfxfDlHaAxAf0fY1ZnniZw3P0WMOv"
    ): ApodResponse

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"

        fun create(): ApodApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApodApiService::class.java)
        }
    }
}
