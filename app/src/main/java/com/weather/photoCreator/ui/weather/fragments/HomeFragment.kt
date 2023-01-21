package com.weather.photoCreator.ui.weather.fragments

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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


    override fun initView() {
        baseViewBinding.test.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectImageFragment)
        }
    }



    override fun getContentView() = R.layout.fragment_home


    override fun initializeViewModel() {
        baseViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }
}