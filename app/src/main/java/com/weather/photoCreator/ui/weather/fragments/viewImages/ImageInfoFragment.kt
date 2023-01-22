package com.weather.photoCreator.ui.weather.fragments.viewImages

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.FileProvider
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.weather.photoCreator.R
import com.weather.photoCreator.base.BaseFragment
import com.weather.photoCreator.base.BaseViewModel
import com.weather.photoCreator.databinding.FragmentImageInfoBinding
import com.weather.photoCreator.utils.FileHelper.deleteFileUsingUri
import timber.log.Timber
import java.io.File

class ImageInfoFragment: BaseFragment<BaseViewModel, FragmentImageInfoBinding>(){

    val args: ImageInfoFragmentArgs by navArgs()

    override fun initView() {
        baseViewBinding.image.setImageURI(args.imageUri)
        baseViewBinding.delete.setOnClickListener {
            if(deleteFileUsingUri(args.imageUri , requireContext())){
                Timber.e("File Deleted")
                showShortToaster(requireContext() , "Image Deleted Successfully")
                findNavController().navigateUp()
            }
        }
        baseViewBinding.share.setOnClickListener {
            shareFile()
        }
    }

    private fun shareFile(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        val file = File(args.imageUri.path.toString())
        val uri = FileProvider.getUriForFile(requireContext(), context?.packageName + ".provider", file)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        val chooser = Intent.createChooser(intent, "Share File Via")

        val infoList: List<ResolveInfo>? =
            requireContext().packageManager?.queryIntentActivities(
                chooser,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        infoList?.let {
            for (resolveInfo in it) {
                val packageName = resolveInfo.activityInfo.packageName
                activity?.grantUriPermission(
                    packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }

            startActivity(chooser)
        }

    }

    override fun handleMenu(){
        val host: MenuHost = requireActivity()
        host.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu , menu)
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner , Lifecycle.State.RESUMED)
    }

    override fun getContentView(): Int {
        return R.layout.fragment_image_info
    }

}