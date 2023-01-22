package com.weather.photoCreator.ui.weather.fragments.home

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseAdapterItemClickListener
import com.weather.photoCreator.base.BaseFragment
import com.weather.photoCreator.databinding.FragmentHomeBinding
import com.weather.photoCreator.ui.weather.fragments.home.adapters.ImagesAdapter
import com.weather.photoCreator.utils.DataState
import com.weather.photoCreator.utils.ViewExtensions.setGone
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import com.weather.photoCreator.utils.ViewExtensions.setVisible
import java.io.File


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {


    override fun initView() {
        baseViewModel?.getSavedFiles()
        baseViewBinding.addNewImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectImageFragment)
        }

    }

    override fun subscribeObservers() {
        super.subscribeObservers()
        observeSavedFiles()
    }

    private fun observeSavedFiles(){
        lifecycleScope.launchWhenCreated {
            baseViewModel?.fileState?.collect{ dataState->
                when(dataState){
                    is DataState.Data ->{
                       if(dataState.data.isNullOrEmpty()){
                           baseViewBinding.emptyView.setVisible()
                           baseViewBinding.imagesRv.setGone()
                       }else{
                           baseViewBinding.emptyView.setGone()
                           baseViewBinding.imagesRv.setVisible()
                           setupGridAdapter(dataState.data)
                       }
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

    private fun setupGridAdapter(data: Array<out File>){
        val adapter = ImagesAdapter()
        baseViewBinding.imagesRv.layoutManager = GridLayoutManager(context ,4
            , LinearLayoutManager.VERTICAL , false
        )
        data.let {
            if (it.isEmpty()) return

            baseViewBinding.imagesRv.setVisible()
            baseViewBinding.emptyView.setGone()

            adapter.apply {
                submitMyList(
                    it.toMutableList() ,
                    object: BaseAdapterItemClickListener<File>{
                        override fun onItemClicked(itemModel: File , position:Int) {
                            val direction = HomeFragmentDirections.actionHomeFragmentToImageInfoFragment(itemModel.toUri())
                            findNavController().navigate(direction)
                        }

                    }
                )
            }
            baseViewBinding.imagesRv.adapter = adapter
            return
        }



    }


    override fun handleMenu(){
        val host: MenuHost = requireActivity()
        host.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        })
    }



    override fun getContentView() = R.layout.fragment_home


    override fun initializeViewModel() {
        baseViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }
}