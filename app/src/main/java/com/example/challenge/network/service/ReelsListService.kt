package com.example.challenge.network.service

import com.example.challenge.data.response.ReelsListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ReelsListService {
    @GET("/reels/list")
    fun getReelsList(
        @Header("Authorization") auth: String
    ): Call<ReelsListResponse>
}