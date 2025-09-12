package com.example.tobjobs.Repo

import com.example.tobjobs.Domain.RegisterResponse
import com.example.tobjobs.Domain.User
import com.example.tobjobs.NetWorkService.RegisterDto

interface RegisterRepository {
    suspend fun register(user:RegisterDto):RegisterResponse
}

sealed class RegisterState{
    data object Idle: RegisterState()
    data class Success(val user: User): RegisterState()
    data class Error(val error:String): RegisterState()
    data object Loading: RegisterState()
}