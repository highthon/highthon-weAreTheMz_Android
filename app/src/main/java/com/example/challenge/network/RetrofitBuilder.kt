package com.example.challenge.network

import com.example.challenge.data.RetrofitClient
import com.example.challenge.network.service.ReelsListService
import com.example.challenge.network.service.SendReelsService
import com.example.challenge.network.service.SignInService
import com.example.challenge.network.service.SignUpService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitBuilder {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val signInService = retrofit.create(SignInService::class.java)
    val sendReelsService = retrofit.create(SendReelsService::class.java)
    val reelsListService = retrofit.create(ReelsListService::class.java)
    val signUpService = retrofit.create(SignUpService::class.java)
}