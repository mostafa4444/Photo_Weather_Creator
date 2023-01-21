package com.weather.photoCreator.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    abstract fun stop()
    abstract fun start()

}