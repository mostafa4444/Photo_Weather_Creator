package com.weather.photoCreator.ui.weather.fragments

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseFragment
import com.weather.photoCreator.databinding.FragmentHomeBinding
import com.weather.photoCreator.ui.weather.fragments.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


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