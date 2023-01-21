package com.weather.photoCreator.ui.weather.fragments

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseFragment
import com.weather.photoCreator.databinding.FragmentSelectImageBinding
import com.weather.photoCreator.ui.weather.activities.MainActivity
import com.weather.photoCreator.ui.weather.fragments.viewModels.SelectImageViewModel
import com.weather.photoCreator.utils.DataState
import com.weather.photoCreator.utils.DialogBuilder.showNegativeDialog
import com.weather.photoCreator.utils.DialogBuilder.showPositiveDialog
import com.weather.photoCreator.utils.IntentHelper.openSettingIntent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


@AndroidEntryPoint
class SelectImageFragment : BaseFragment<SelectImageViewModel , FragmentSelectImageBinding>() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var selectedUri: Uri ?= null
    override fun initView() {
        handleMenu()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        captureImage()
    }

    override fun subscribeObservers() {
        super.subscribeObservers()
        observeWeatherData()
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
            selectedUri = result.data?.data
            getLastLocation()
        }
    }


    override fun getContentView(): Int {
        return R.layout.fragment_select_image
    }

    override fun initializeViewModel() {
        baseViewModel = ViewModelProvider(this)[SelectImageViewModel::class.java]
    }

    private fun getLastLocation(){
        if (ContextCompat.checkSelfPermission(requireActivity() , android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                it?.let {
                    baseViewModel?.getWeatherByLocation(it.longitude , it.latitude)
                }
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

    private fun observeWeatherData(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.weatherState?.collect{ dataState->
                when(dataState){
                    is DataState.Data ->{
                        baseViewBinding.weatherInfo.container.visibility = View.VISIBLE
                        baseViewBinding.weatherInfo.status.text = dataState.data?.weather?.first()?.description
                        baseViewBinding.weatherInfo.tempTxt.text = dataState.data?.main?.temp?.roundToInt().toString()
                        baseViewBinding.weatherInfo.locationTxt.text = "${dataState.data?.sys?.country}, ${dataState.data?.name}"

                        baseViewBinding.weatherInfo.dateTxt.text = dataState?.data?.dt?.toLong()
                            ?.let { getDateFormatted(it) }
                    }
                    is DataState.Error ->{
                        showLongToaster(requireContext() , dataState.msg)
                    }
                    is DataState.Loading ->{
                        Timber.e(dataState.progressBarState.toString())
                    }
                }
            }
        }
    }

    private fun getDateFormatted(date: Long): String{
        try {
            val calender = Calendar.getInstance()
            calender.time.time = date
            val formatter = SimpleDateFormat("HH:mm dd MM" , Locale.getDefault())
            return formatter.format(calender.time)
        }catch (e: Exception){
            return  ""
        }
    }

    private fun handleMenu(){
        val host: MenuHost = requireActivity()
        host.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu , menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save){
                    if (selectedUri == null){
                        (requireActivity() as MainActivity).showNegativeDialog(
                            "Select Photo",
                            "You have to capture or selecting image to continue",
                            "OK", null
                        )
                    }
                    return true
                }else if (menuItem.itemId == R.id.captureImage){
                    captureImage()
                    return true
                }

                return false
            }

        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.save_menu , menu)
//        menu.findItem(R.id.save)?.let {
//            it.setOnMenuItemClickListener {
//
//                return@setOnMenuItemClickListener true
//            }
//        }
//    }
}