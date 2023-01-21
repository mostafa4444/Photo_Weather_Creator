package com.weather.photoCreator.base

import android.content.Context
import android.widget.Toast

interface BaseToasters {
    fun showShortToaster(context: Context , msg: String){
        Toast.makeText(context , msg , Toast.LENGTH_SHORT).show()
    }

    fun showLongToaster(context: Context , msg: String){
        Toast.makeText(context , msg , Toast.LENGTH_LONG).show()
    }
}