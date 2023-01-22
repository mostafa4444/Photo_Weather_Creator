package com.weather.photoCreator.utils

import android.view.View

object ViewExtensions {

    fun View.setVisible(){
        this.visibility = View.VISIBLE
    }
    fun View.setGone(){
        this.visibility = View.GONE
    }
}