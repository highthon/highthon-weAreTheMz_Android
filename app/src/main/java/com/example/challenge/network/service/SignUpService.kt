package com.example.challenge.network.service

import com.example.challenge.data.request.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/auth/signup")
    fun signUp(
        @Body body: SignUpRequest
    ): Call<Void>
}