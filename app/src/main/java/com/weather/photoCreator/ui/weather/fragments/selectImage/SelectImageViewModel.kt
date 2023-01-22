package com.weather.photoCreator.ui.weather.fragments.selectImage

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.weather.photoCreator.base.BaseViewModel
import com.weather.photoCreator.models.response.WeatherResponse
import com.weather.photoCreator.repo.remote.repositories.WeatherRepositoryImpl
import com.weather.photoCreator.repo.remote.usecases.GetWeatherUseCase
import com.weather.photoCreator.utils.DataState
import com.weather.photoCreator.utils.ProgressBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectImageViewModel @Inject constructor(
    private val weatherUseCase: GetWeatherUseCase
): BaseViewModel() {

    private val _weatherState: MutableStateFlow<DataState<WeatherResponse>> = MutableStateFlow(
        DataState.Loading(ProgressBarState.Idle)
    )
    val weatherState: StateFlow<DataState<WeatherResponse>> = _weatherState

    fun getWeatherByLocation(longitude: Double , latitude: Double){
        weatherUseCase.executeApi(longitude, latitude).onEach { dataState->
            when(dataState){
                is DataState.Data ->{
                    _weatherState.value = dataState.copy(data = dataState.data)
                }
                is DataState.Error ->{
                    _weatherState.value = dataState.copy(msg = dataState.msg)
                }
                is DataState.Loading ->{
                    _weatherState.value = dataState.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(viewModelScope)
    }

}