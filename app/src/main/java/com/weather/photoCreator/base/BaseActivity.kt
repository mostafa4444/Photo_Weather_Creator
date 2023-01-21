package com.weather.photoCreator.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.weather.photoCreator.utils.DIALOG_TYPE
import com.weather.photoCreator.utils.DialogBuilder

abstract class BaseActivity<VM: BaseViewModel , VB: ViewDataBinding> : AppCompatActivity() {
//    18ef10282493b277337a837976fea196


    protected var baseViewModel: VM ?= null
    protected lateinit var baseViewBinding: VB
    protected abstract fun initView()
    protected abstract fun getContentView(): Int
    protected open fun initializeViewModel(){}
    protected open fun subscribeObservers(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewBinding = DataBindingUtil.setContentView(this, getContentView())
        initializeViewModel()
        initView()
        baseViewModel?.start()
        subscribeObservers()
    }

    fun showPositiveDialog(title: String , msg: String , btnTitle: String , positiveBlock: (() -> Unit)? ){
        DialogBuilder.showDialog(this , DIALOG_TYPE.POSITIVE_ONLY , title  , btnTitle, null,
            msg , null , positiveBlock)
    }

    fun showNegativeDialog(title: String , msg: String , btnTitle: String , negativeBlock: (() -> Unit)?){
        DialogBuilder.showDialog(this , DIALOG_TYPE.NEGATIVE_ONLY , title  , btnTitle, null,
            msg , null , negativeBlock)
    }
    fun showDialog(title: String , msg: String , posTitle: String , negTitle: String , positiveBlock: (() -> Unit)? , negativeBlock: (() -> Unit)?){
        DialogBuilder.showDialog(this , DIALOG_TYPE.BOTH , title , posTitle , negTitle,
            msg , positiveBlock , negativeBlock)
    }



}