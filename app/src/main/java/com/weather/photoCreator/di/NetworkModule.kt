package com.weather.photoCreator.di

import com.weather.photoCreator.repo.remote.services.WeatherService
import com.weather.photoCreator.utils.AppConstants.BASE_URL
import com.weather.photoCreator.utils.AppConstants.TIMEOUT
import com.weather.photoCreator.utils.AppConstants.TIME_UNIT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @ViewModelScoped
    @Provides
    fun provideBaseURL() = BASE_URL

    @ViewModelScoped
    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @ViewModelScoped
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .callTimeout(TIMEOUT , TIME_UNIT)
            .connectTimeout(TIMEOUT , TIME_UNIT)
            .writeTimeout(TIMEOUT , TIME_UNIT)
            .readTimeout(TIMEOUT , TIME_UNIT)
            .build()

    @ViewModelScoped
    @Provides
    fun provideConverterFactory() = GsonConverterFactory.create()

    @ViewModelScoped
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converter: Converter.Factory,
        okkHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converter)
        .client(okkHttpClient)
        .build()

    @ViewModelScoped
    @Provides
    fun provideWeatherService(retrofit: Retrofit) =
        retrofit.create(WeatherService::class.java)

}