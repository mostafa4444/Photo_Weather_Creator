package com.weather.photoCreator.repo.remote.usecases

import android.content.Context
import com.weather.photoCreator.models.response.WeatherResponse
import com.weather.photoCreator.repo.remote.repositories.WeatherRepositoryImpl
import com.weather.photoCreator.ui.weather.fragments.home.FILES
import com.weather.photoCreator.utils.DataState
import com.weather.photoCreator.utils.FileHelper
import com.weather.photoCreator.utils.ProgressBarState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

class GetSavedFilesUseCase  @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun fetchSavedFiles(): Flow<DataState<Array <out File>>> =
        flow{
            try {
//                emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
                val data = FileHelper.getAllSavedFiles(context)
                emit(DataState.Data(data))
            } catch (e: Exception){
                emit(DataState.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

}