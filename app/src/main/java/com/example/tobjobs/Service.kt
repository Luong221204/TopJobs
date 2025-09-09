package com.example.tobjobs

import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    @POST("/auth/login")
    suspend fun login(@Body user: LoginDto): LoginResponse
}

data class LoginDto(
    val email: String,
    val password: String
)