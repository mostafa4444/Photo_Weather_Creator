package com.weather.photoCreator.utils

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

object IntentHelper {

    fun AppCompatActivity.openSettingIntent(packageName: String){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        this.startActivity(intent)
    }

    fun getDateFormatted(date: Long): String{
        try {
            val calender = Calendar.getInstance()
            calender.time.time = date
            val formatter = SimpleDateFormat("HH:mm dd MM" , Locale.getDefault())
            return formatter.format(calender.time)
        }catch (e: Exception){
            return  ""
        }
    }
}