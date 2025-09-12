package com.example.tobjobs.Domain


data class LoginResponse(
    val user: User?,
    val message:String,
)

data class RegisterResponse(
    val user: User?,
    val message:List<String> = emptyList(),
)
data class User(
    val id:String = "",
    val fullName :String = "",
    val email:String ="",
    val isVerified :Boolean =false,
    val role :List<String> = emptyList()
)

data class Error(
    val message: List<String> = emptyList(),
    val error: String,
    val statusCode :Int
)