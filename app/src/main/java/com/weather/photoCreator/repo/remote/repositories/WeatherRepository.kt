package com.weather.photoCreator.repo.remote.repositories

import com.weather.photoCreator.models.response.WeatherResponse
import retrofit2.Response

interface WeatherRepository {

    suspend fun getWeatherInfo(longitude: Double , latitude: Double): Response<WeatherResponse>

}