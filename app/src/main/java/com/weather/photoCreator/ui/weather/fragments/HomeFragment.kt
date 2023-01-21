package com.weather.photoCreator.ui.weather.fragments

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseFragment
import com.weather.photoCreator.databinding.FragmentHomeBinding
import com.weather.photoCreator.ui.weather.activities.MainActivity
import com.weather.photoCreator.ui.weather.fragments.viewModels.HomeViewModel
import com.weather.photoCreator.utils.DialogBuilder.showPositiveDialog
import com.weather.photoCreator.utils.IntentHelper.openSettingIntent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel , FragmentHomeBinding>() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun initView() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()
    }

    private fun getLastLocation(){
        if (ContextCompat.checkSelfPermission(requireActivity() , android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                Timber.e("Latitude ${it.latitude.toString()}, Longitude ${it.longitude.toString()}")
            }
        }else{
            (requireActivity() as MainActivity).showPositiveDialog(getString(R.string.permission_dialog_title) ,
                getString(R.string.permission_dialog_body) , getString(R.string.permission_dialog_positive)
            ) {
                activity?.packageName?.let {
                    (requireActivity() as MainActivity).openSettingIntent(
                        it
                    )
                }
            }
        }
    }

    override fun getContentView() = R.layout.fragment_home


    override fun initializeViewModel() {
        baseViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }
}