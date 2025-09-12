package com.example.tobjobs.RepoImpl

import com.example.tobjobs.NetWorkService.LoginDto
import com.example.tobjobs.Domain.LoginResponse
import com.example.tobjobs.Repo.LoginRepository
import com.example.tobjobs.NetWorkService.Service
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(
    private val apiService: Service
): LoginRepository {

    override suspend fun loginWithEmail(loginDto: LoginDto): LoginResponse {
        return apiService.login(loginDto)
    }
}