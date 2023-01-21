package com.weather.photoCreator.utils

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


object DialogBuilder {

    fun AppCompatActivity.showPositiveDialog(title: String , msg: String , btnTitle: String , positiveBlock: (() -> Unit)? ){
        DialogBuilder.showDialog(this, DIALOG_TYPE.POSITIVE_ONLY , title  , btnTitle, null,
            msg , null , positiveBlock)
    }

    fun AppCompatActivity.showNegativeDialog(title: String , msg: String , btnTitle: String , negativeBlock: (() -> Unit)?){
        DialogBuilder.showDialog(this , DIALOG_TYPE.NEGATIVE_ONLY , title  , btnTitle, null,
            msg , null , negativeBlock)
    }
    fun AppCompatActivity.showDialog(title: String , msg: String , posTitle: String , negTitle: String , positiveBlock: (() -> Unit)? , negativeBlock: (() -> Unit)?){
        DialogBuilder.showDialog(this , DIALOG_TYPE.BOTH , title , posTitle , negTitle,
            msg , positiveBlock , negativeBlock)
    }

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