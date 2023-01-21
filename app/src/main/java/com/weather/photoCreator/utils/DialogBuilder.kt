package com.weather.photoCreator.utils

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


object DialogBuilder {

    fun showDialog(
        activity: AppCompatActivity,
        type: DIALOG_TYPE,
        title: String,
        positiveTitle: String?,
        negativeTitle: String?,
        msg: String,
        positiveBlock: (() -> Unit)? , negativeBlock: (() -> Unit)?){

        if (type == DIALOG_TYPE.POSITIVE_ONLY){
            AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveTitle) { dialog, which ->
                    dialog.dismiss()
                    positiveBlock?.invoke()
                }
                .show()
        }else if (type == DIALOG_TYPE.NEGATIVE_ONLY){
            AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(negativeTitle){dialog, which->
                    dialog.dismiss()
                    negativeBlock?.invoke()
                }
                .show()
        }else{
            AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveTitle) { dialog, which ->
                    dialog.dismiss()
                    positiveBlock?.invoke()
                }
                .setNegativeButton(negativeTitle){dialog, which->
                    dialog.dismiss()
                    negativeBlock?.invoke()
                }
                .show()
        }


    }

}

enum class DIALOG_TYPE{
    POSITIVE_ONLY,
    NEGATIVE_ONLY,
    BOTH
}