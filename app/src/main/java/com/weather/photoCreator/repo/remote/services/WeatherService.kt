package com.weather.photoCreator.repo.remote.services

import com.weather.photoCreator.models.response.WeatherResponse
import com.weather.photoCreator.utils.AppConstants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeatherInfo(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("appid") appID: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}