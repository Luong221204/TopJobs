package com.example.tobjobs


data class LoginResponse(
    val user: User?,
    val message:String,
)


data class User(
    val id:String = "",
    val fullName :String = "",
    val email:String ="",
    val isVerified :Boolean =false,
    val role :List<String> = emptyList()
)
