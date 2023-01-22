package com.weather.photoCreator.ui.weather.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseActivity
import com.weather.photoCreator.base.BaseViewModel
import com.weather.photoCreator.databinding.ActivityMainBinding
import com.weather.photoCreator.utils.IntentHelper.openSettingIntent
import dagger.hilt.android.AndroidEntryPoint
import com.weather.photoCreator.utils.DialogBuilder.showDialog



@AndroidEntryPoint
class MainActivity : BaseActivity<BaseViewModel , ActivityMainBinding>() {

    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    lateinit var navController: NavController
    lateinit var navHost: NavHostFragment

    override fun initView() {
        initNavHost()
        if (!hasPermissions(PERMISSIONS))
            askPermissions()
    }

    override fun getContentView() = R.layout.activity_main

    private fun initNavHost(){
        navHost = supportFragmentManager.findFragmentById(baseViewBinding.weatherFragment.id) as NavHostFragment
        navController = navHost.navController
    }

    private fun askPermissions() {
        if (!hasPermissions(PERMISSIONS)) {
            requestPermissionLauncher.launch(PERMISSIONS)
        }
    }

    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGranted ->
            if (isGranted.containsValue(false)) {
                this.showDialog(
                    getString(R.string.permission_dialog_title),
                    getString(R.string.permission_dialog_body),
                    getString(R.string.permission_dialog_positive),
                    getString(R.string.permission_dialog_negative) ,{
                        this.openSettingIntent(packageName)
                    } , {
                        this.finishAffinity()
                    })
            }
        }

    private fun hasPermissions(permissions: Array<String>?): Boolean {
        if (permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
            return true
        }
        return false
    }

}