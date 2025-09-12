package com.example.tobjobs.di

import com.example.tobjobs.RepoImpl.LoginRepoImpl
import com.example.tobjobs.Repo.LoginRepository
import com.example.tobjobs.Repo.RegisterRepository
import com.example.tobjobs.RepoImpl.RegisterRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepoModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLoginRepository(
        impl: LoginRepoImpl
    ): LoginRepository


    @Binds
    @ViewModelScoped
    abstract fun bindRegisterRepository(
        impl: RegisterRepoImpl
    ):RegisterRepository
}