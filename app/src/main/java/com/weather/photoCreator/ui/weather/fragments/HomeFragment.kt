package com.weather.photoCreator.ui.weather.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseFragment
import com.weather.photoCreator.databinding.FragmentHomeBinding
import com.weather.photoCreator.ui.weather.fragments.viewModels.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel , FragmentHomeBinding>() {
    override fun initView() {

    }

    override fun getContentView() = R.layout.fragment_home


    override fun initializeViewModel() {
        val viewModel: HomeViewModel by viewModels()
        baseViewModel = viewModel
    }
}