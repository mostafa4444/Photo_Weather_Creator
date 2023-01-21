package com.weather.photoCreator.repo.remote.repositories

import com.weather.photoCreator.models.response.WeatherResponse
import com.weather.photoCreator.repo.remote.services.WeatherService
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherService
) : WeatherRepository{
    override suspend fun getWeatherInfo(longitude: Double , latitude: Double): Response<WeatherResponse> {
        return apiService.getWeatherInfo( longitude,latitude)
    }
}