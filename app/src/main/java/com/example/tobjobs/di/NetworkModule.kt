package com.example.tobjobs.di

import com.example.cv.LoginRepoImpl
import com.example.tobjobs.LoginRepository
import com.example.tobjobs.Service
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun getGson():Gson{
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun getApiService(gson: Gson):Service{
        return Retrofit.Builder().baseUrl("https://tuyendungbackend.onrender.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(Service::class.java)
    }

    @Provides
    fun provideLoginRepo(loginImpl: LoginRepoImpl): LoginRepository = loginImpl
}