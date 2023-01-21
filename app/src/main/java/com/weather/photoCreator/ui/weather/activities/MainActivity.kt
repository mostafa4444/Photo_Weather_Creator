package com.weather.photoCreator.ui.weather.activities

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseActivity
import com.weather.photoCreator.base.BaseViewModel
import com.weather.photoCreator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<BaseViewModel , ActivityMainBinding>() {

    lateinit var navController: NavController
    lateinit var navHost: NavHostFragment

    override fun initView() {
        initNavHost()
    }

    override fun getContentView() = R.layout.activity_main

    private fun initNavHost(){
        navHost = supportFragmentManager.findFragmentById(baseViewBinding.weatherFragment.id) as NavHostFragment
        navController = navHost.navController
    }

}