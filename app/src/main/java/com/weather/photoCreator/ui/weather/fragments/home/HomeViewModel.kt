package com.weather.photoCreator.ui.weather.fragments.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.photoCreator.base.BaseViewModel
import com.weather.photoCreator.models.response.WeatherResponse
import com.weather.photoCreator.repo.remote.usecases.GetSavedFilesUseCase
import com.weather.photoCreator.utils.DataState
import com.weather.photoCreator.utils.FileHelper
import com.weather.photoCreator.utils.ProgressBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

typealias FILES = DataState<Array<out File>>
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSavedFilesUseCase: GetSavedFilesUseCase
) : BaseViewModel() {

    private val _fileState: MutableStateFlow<FILES> = MutableStateFlow(
        DataState.Loading(ProgressBarState.Idle)
    )
    val fileState: StateFlow<FILES> = _fileState

    fun getSavedFiles(){
         getSavedFilesUseCase.fetchSavedFiles().onEach{dataState->
             when(dataState){
                 is DataState.Data ->{
                     _fileState.value = dataState.copy(data = dataState.data)
                 }
                 is DataState.Error ->{
                     _fileState.value = dataState.copy(msg = dataState.msg)
                 }
                 is DataState.Loading ->{
                     _fileState.value = dataState.copy(progressBarState = dataState.progressBarState)
                 }
             }
        }.launchIn(viewModelScope)
    }

}