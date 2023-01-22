package com.weather.photoCreator.di

import com.weather.photoCreator.repo.remote.repositories.WeatherRepository
import com.weather.photoCreator.repo.remote.repositories.WeatherRepositoryImpl
import com.weather.photoCreator.repo.remote.services.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object RepoModules {

    @ViewModelScoped
    @Provides
    fun provideWeatherRepo(service: WeatherService): WeatherRepository =
        WeatherRepositoryImpl(apiService = service)

    @ViewModelScoped
    @Provides
    fun provideWeatherService(retrofit: Retrofit) =
        retrofit.create(WeatherService::class.java)


}