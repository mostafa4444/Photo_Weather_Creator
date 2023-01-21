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
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideBaseURL() = BASE_URL

    @Singleton
    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .callTimeout(TIMEOUT , TIME_UNIT)
            .connectTimeout(TIMEOUT , TIME_UNIT)
            .writeTimeout(TIMEOUT , TIME_UNIT)
            .readTimeout(TIMEOUT , TIME_UNIT)
            .build()

//    @Singleton
//    @Provides
//    fun provideConverterFactory() = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okkHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okkHttpClient)
        .build()


}