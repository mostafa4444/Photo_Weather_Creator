package com.weather.photoCreator.repo.remote.usecases

import com.weather.photoCreator.models.response.WeatherResponse
import com.weather.photoCreator.repo.remote.repositories.WeatherRepositoryImpl
import com.weather.photoCreator.utils.DataState
import com.weather.photoCreator.utils.ProgressBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepositoryImpl
) {

    fun executeApi(longitude: Double , latitude: Double): Flow<DataState<WeatherResponse>> =
        flow{
           try {
               emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
               val data = weatherRepository.getWeatherInfo(longitude, latitude)
               if (data.isSuccessful && data.code() == 200)
                   emit(DataState.Data(data.body()))
               else
                   emit(DataState.Error("Something went wrong"))
           } catch (e: Exception){
               emit(DataState.Error(e.message.toString()))
           }
        }.flowOn(Dispatchers.IO)

}