package com.weather.photoCreator.utils

sealed class DataState <T>{

    data class Error<T>(
        val msg: String
    ): DataState<T>()

    data class Data<T>(
        val data: T ?= null
    ): DataState<T>()

    data class Loading<T>(
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ): DataState<T>()
}


sealed class ProgressBarState{
    object Loading: ProgressBarState()
    object Idle: ProgressBarState()
}