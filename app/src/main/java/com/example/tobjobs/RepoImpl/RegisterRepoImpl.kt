package com.example.tobjobs.RepoImpl

import com.example.tobjobs.Domain.RegisterResponse
import com.example.tobjobs.NetWorkService.RegisterDto
import com.example.tobjobs.NetWorkService.Service
import com.example.tobjobs.Repo.RegisterRepository
import javax.inject.Inject

class RegisterRepoImpl @Inject
constructor(
    private val service: Service
):RegisterRepository{
    override suspend fun register(user: RegisterDto): RegisterResponse {
        return service.register(user)
    }
}