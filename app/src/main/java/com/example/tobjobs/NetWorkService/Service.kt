package com.example.tobjobs.NetWorkService

import com.example.tobjobs.Domain.LoginResponse
import com.example.tobjobs.Domain.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    @POST("/auth/login")
    suspend fun login(@Body user: LoginDto): LoginResponse

    @POST("/auth/register/user")
    suspend fun register(@Body user :RegisterDto):RegisterResponse
}

data class LoginDto(
    val email: String,
    val password: String
)

data class RegisterDto(
    val fullName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val agreement: Boolean
)