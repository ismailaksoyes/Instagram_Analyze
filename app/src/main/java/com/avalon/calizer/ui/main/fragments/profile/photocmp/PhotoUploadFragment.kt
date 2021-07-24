package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.photoanalyze.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoUploadBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.jar.Manifest
import javax.inject.Inject

@AndroidEntryPoint
class PhotoUploadFragment : Fragment() {
    private lateinit var binding: FragmentPhotoUploadBinding

    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.resultCode == IMAGE_CHOOSE) {

                result.data?.let { itImagesIntent ->
                    getImageResultData(itImagesIntent)
                }
            }
        }

    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val urlList = ArrayList<String>()
        urlList.add("https://witandfolly.co/wp-content/uploads/2020/01/Hoka-14-1-scaled.jpg")
        setGlideImageUrl(urlList)
        RealTimeData()

    }

    private fun getImageResultData(imagesIntent: Intent) {
        val imageList = ArrayList<Bitmap>()
        imagesIntent.clipData?.let {

            // it.forEach { imageUri->
            // imageList.add(BitmapFactory.decodeFile(imageUri))
            Log.d("ImageLog", " ${it.itemCount}")
            // }
        }

    }


    private fun getGalleryImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startForResult.launch(intent)

    }

    private fun onclickRequestPermission(view: View){
      val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(view.context,permission)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(permission), PERMISSION_CODE)
        }
    }
    fun getBitmap(bitmap: Bitmap) {
        val list = ArrayList<PhotoAnalyzeData>()
        for (i in 0L..5L) {
            list.add(PhotoAnalyzeData(i, bitmap, false))
        }
        val newList = list.toTypedArray()
        viewModel.setPhotoData(list)
        binding.btnUploadImage.setOnClickListener {
            // val action = PhotoUploadFragmentDirections.actionPhotoUploadFragmentToPhotoAnalyzeLoadingFragment(newList)
            //findNavController().navigate(action)
            onclickRequestPermission(it)
            getGalleryImage()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }

    fun RealTimeData() {
        lifecycleScope.launch {
            while (true) {
                delay(1000)
                viewModel.randomData((0..20).random().toInt())
            }
        }
    }

    fun setGlideImageUrl(urlList: ArrayList<String>) {
        urlList.forEach { imageUrl ->
            Glide.with(this).asBitmap().load(imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        getBitmap(bitmap = resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
        }
    }


}


