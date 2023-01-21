package com.weather.photoCreator.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

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




}