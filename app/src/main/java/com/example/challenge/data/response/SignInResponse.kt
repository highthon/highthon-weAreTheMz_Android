package com.example.challenge.data.response

import java.time.LocalDateTime

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: String
)