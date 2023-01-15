package com.example.challenge.network.service

import com.example.challenge.data.request.SendReelsRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SendReelsService {
    @Multipart
    @POST("/reels")
    fun sendReels(
        @Header("Authorization") auth: String,
        @Part("requestDto") body: SendReelsRequest,
        @Part file: MultipartBody.Part
    ): Call<Void>
}