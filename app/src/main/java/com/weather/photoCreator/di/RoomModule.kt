package com.weather.photoCreator.di

import android.content.Context
import androidx.room.Room
import com.weather.photoCreator.repo.local.WeatherDatabase
import com.weather.photoCreator.repo.remote.repositories.WeatherRepository
import com.weather.photoCreator.repo.remote.repositories.WeatherRepositoryImpl
import com.weather.photoCreator.repo.remote.services.WeatherService
import com.weather.photoCreator.utils.AppConstants.WEATHER_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object RoomModule {

    @ViewModelScoped
    @Provides
    fun provideRooDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            WEATHER_DATABASE_NAME
        ).build()


    @ViewModelScoped
    @Provides
    fun provideWeatherDao(database: WeatherDatabase) = database.weatherDao()

    @ViewModelScoped
    @Provides
    fun provideWeatherRepo(service: WeatherService): WeatherRepository =
        WeatherRepositoryImpl(apiService = service)

    @ViewModelScoped
    @Provides
    fun provideWeatherService(retrofit: Retrofit) =
        retrofit.create(WeatherService::class.java)


}