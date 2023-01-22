package com.weather.photoCreator.ui.weather.fragments.selectImage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseFragment
import com.weather.photoCreator.databinding.FragmentSelectImageBinding
import com.weather.photoCreator.ui.weather.activities.MainActivity
import com.weather.photoCreator.utils.DataState
import com.weather.photoCreator.utils.DialogBuilder.showNegativeDialog
import com.weather.photoCreator.utils.DialogBuilder.showPositiveDialog
import com.weather.photoCreator.utils.FileHelper
import com.weather.photoCreator.utils.IntentHelper.getDateFormatted
import com.weather.photoCreator.utils.IntentHelper.openSettingIntent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.roundToInt


@AndroidEntryPoint
class SelectImageFragment : BaseFragment<SelectImageViewModel, FragmentSelectImageBinding>() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var selectedUri: Uri ?= null
    private var pickImageIntentLauncher : ActivityResultLauncher<Intent>? = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            baseViewBinding.image.setImageURI(result.data?.data)
            selectedUri = result.data?.data
            Timber.e("Launcher $selectedUri")
            getLastLocation()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        pickImageIntentLauncher = null
    }

    override fun initView() {
        baseViewBinding
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        captureImage()
    }

    override fun subscribeObservers() {
        super.subscribeObservers()
        observeWeatherData()
    }

    private fun captureImage(){
        ImagePicker.with(this).createIntent { intent ->
            pickImageIntentLauncher?.launch(intent)
            return@createIntent
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
        lifecycleScope.launchWhenCreated {
            baseViewModel?.weatherState?.collect{ dataState->
                when(dataState){
                    is DataState.Data ->{
                        baseViewBinding.weatherInfo.container.visibility = View.VISIBLE
                        baseViewBinding.weatherInfo.status.text = dataState.data?.weather?.first()?.description
                        baseViewBinding.weatherInfo.tempTxt.text = dataState.data?.main?.temp?.roundToInt().toString()
                        baseViewBinding.weatherInfo.locationTxt.text = "${dataState.data?.sys?.country}, ${dataState.data?.name}"

                        baseViewBinding.weatherInfo.dateTxt.text = dataState.data?.dt?.toLong()
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


    override fun handleMenu(){
        val host: MenuHost = requireActivity()
        host.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.save_menu , menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.save){
                    if (selectedUri != null) {
                        if (FileHelper.createDirectoryAndSaveFile(
                                requireContext(),
                                drawMyView(baseViewBinding.containerView)
                            )
                        ) {
                            showShortToaster(context = requireContext(), "Image Saved Successfully")
                            selectedUri = null
                            findNavController().navigateUp()
                        }
                    }else{
                        (requireActivity() as MainActivity).showNegativeDialog(
                            "Image Require",
                            "Please capture or select image to proceed",
                            "OK" , null
                        )
                    }
                }else if (menuItem.itemId == R.id.add_new){
                    captureImage()
                }

               return true
            }

        } ,viewLifecycleOwner, Lifecycle.State.RESUMED)
    }



    fun drawMyView(v: View): Bitmap{
        val bitmap = Bitmap.createBitmap(
            v.width,
            v.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas= Canvas(bitmap)
        baseViewBinding.containerView.draw(canvas)
        return bitmap
    }

}