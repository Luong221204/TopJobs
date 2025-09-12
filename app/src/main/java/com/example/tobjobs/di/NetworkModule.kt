package com.example.tobjobs.di

import com.example.tobjobs.NetWorkService.Service
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
    fun getTopJobsRetrofit(gson: Gson):Retrofit{
        return Retrofit.Builder().baseUrl("https://tuyendungbackend.onrender.com")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }
    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }


}