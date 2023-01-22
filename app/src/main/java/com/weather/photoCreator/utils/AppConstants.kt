package com.weather.photoCreator.utils

import java.util.concurrent.TimeUnit

object AppConstants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "18ef10282493b277337a837976fea196"
    val TIME_UNIT = TimeUnit.SECONDS
    const val TIMEOUT = 60L
    const val WEATHER_DATABASE_NAME = "weather_db"
    const val CREATED_FILE_PATH = "WeatherPhotoCreator"
}