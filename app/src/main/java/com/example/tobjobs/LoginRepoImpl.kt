package com.example.cv

import com.example.tobjobs.LoginDto
import com.example.tobjobs.LoginRepository
import com.example.tobjobs.LoginResponse
import com.example.tobjobs.Service
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(
    private val apiService: Service
): LoginRepository {

    override suspend fun loginWithEmail(loginDto: LoginDto): LoginResponse {
        return apiService.login(loginDto)
    }
}