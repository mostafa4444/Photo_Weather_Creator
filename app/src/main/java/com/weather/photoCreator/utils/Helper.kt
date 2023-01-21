package com.weather.photoCreator.utils

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

object IntentHelper {

    fun AppCompatActivity.openSettingIntent(packageName: String){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        this.startActivity(intent)
    }
}