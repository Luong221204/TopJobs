package com.example.tobjobs.Repo

import com.example.tobjobs.NetWorkService.LoginDto
import com.example.tobjobs.Domain.LoginResponse
import com.example.tobjobs.Domain.User

interface LoginRepository {
    suspend fun loginWithEmail(loginDto: LoginDto): LoginResponse
}


sealed class LoginState{
    data object Idle: LoginState()
    data class Success(val user: User): LoginState()
    data class Error(val error:String): LoginState()
    data object Loading: LoginState()

}