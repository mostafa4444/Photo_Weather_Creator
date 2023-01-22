package com.weather.photoCreator.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VM: BaseViewModel , VB: ViewDataBinding> : Fragment() , BaseToasters {


    protected var baseViewModel: VM ?= null
    protected lateinit var baseViewBinding: VB
    protected abstract fun initView()
    protected abstract fun getContentView(): Int
    protected open fun initializeViewModel(){}
    protected abstract fun handleMenu()
    protected open fun subscribeObservers(){}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseViewBinding = DataBindingUtil.inflate(inflater , getContentView() , container , false)
        return baseViewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        baseViewModel?.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleMenu()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        initView()
        subscribeObservers()
    }



}