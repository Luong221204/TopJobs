package com.example.tobjobs

interface LoginRepository {
    suspend fun loginWithEmail(loginDto: LoginDto): LoginResponse
}


sealed class LoginState{
    data object Idle:LoginState()
    data class Success(val user: User): LoginState()
    data class Error(val error:String): LoginState()
    data object Loading: LoginState()

}