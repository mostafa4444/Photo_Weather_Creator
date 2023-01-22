package com.weather.photoCreator.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.core.net.toFile
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.FilenameFilter
import java.util.*

object FileHelper {



    fun createDirectoryAndSaveFile(
        context: Context,
        imageToSave: Bitmap
    ): Boolean {
        val uuid = UUID.randomUUID().toString()
        val dir = File(
            context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            AppConstants.CREATED_FILE_PATH
        )
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, "$uuid.jpg")
        if (file.exists()){
            return true
        }

        val outputStream = FileOutputStream(file)
        imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        outputStream.flush()
        outputStream.close()
        return true

    }

    fun getAllSavedFiles(context: Context): Array<out File>? {
        val dir = File(
            context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            AppConstants.CREATED_FILE_PATH
        )
        val data = dir.listFiles()
        data?.forEach {
            Timber.e("File ${it.name}")
        }
        return dir.listFiles()
    }

    fun deleteFileUsingUri(uri: Uri, context: Context): Boolean{
        try {
            val fileToDelete = File(uri.path.toString())
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    if (fileToDelete.exists()) {
                        fileToDelete.canonicalFile.delete()
                        if (fileToDelete.exists()) {
                            context.deleteFile(fileToDelete.name)
                        }
                    }
                    return true
                }
            }
        }catch (e: Exception){
            return false
        }
        return false
    }
}