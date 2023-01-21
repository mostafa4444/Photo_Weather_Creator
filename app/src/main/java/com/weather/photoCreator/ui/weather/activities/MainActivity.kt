package com.weather.photoCreator.ui.weather.activities

import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseActivity
import com.weather.photoCreator.base.BaseViewModel
import com.weather.photoCreator.databinding.ActivityMainBinding

class MainActivity : BaseActivity<BaseViewModel , ActivityMainBinding>() {

    override fun initView() {

    }

    override fun getContentView() = R.layout.activity_main

}