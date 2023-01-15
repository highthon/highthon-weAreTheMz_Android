package com.example.challenge.network.service

import com.example.challenge.data.request.SignInRequest
import com.example.challenge.data.response.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/auth/signin")
    fun signIn(
        @Body body: SignInRequest
    ): Call<SignInResponse>
}