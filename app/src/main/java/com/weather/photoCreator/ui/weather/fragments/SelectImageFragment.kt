package com.weather.photoCreator.ui.weather.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseFragment
import com.weather.photoCreator.base.BaseViewModel
import com.weather.photoCreator.databinding.FragmentSelectImageBinding
import com.weather.photoCreator.ui.weather.activities.MainActivity
import com.weather.photoCreator.utils.DialogBuilder.showPositiveDialog
import com.weather.photoCreator.utils.IntentHelper.openSettingIntent
import timber.log.Timber


class SelectImageFragment : BaseFragment<BaseViewModel , FragmentSelectImageBinding>() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun initView() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        captureImage()
    }

    private fun captureImage(){
        ImagePicker.with(this).createIntent { intent ->
            pickImageIntentLauncher.launch(intent)
        }
    }

    private val pickImageIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            Timber.e(result.data?.data.toString())
            baseViewBinding.image.setImageURI(result.data?.data)
            baseViewBinding.weatherInfo.container.visibility = View.VISIBLE
            getLastLocation()
        }
    }


    override fun getContentView(): Int {
        return R.layout.fragment_select_image
    }

    override fun initializeViewModel() {
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

}