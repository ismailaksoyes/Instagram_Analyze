package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import javax.inject.Inject

@AndroidEntryPoint
class PhotoUploadFragment : Fragment() {
    private lateinit var binding:FragmentPhotoUploadBinding
    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPhotoUploadBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val urlList = ArrayList<String>()
        urlList.add("https://cdn.bolgegundem.com/d/gallery/1030_4.jpg")
        setGlideImageUrl(urlList)
        RealTimeData()
    }

    fun getBitmap(bitmap: Bitmap){
        val list = ArrayList<PhotoAnalyzeData>()
        for (i in 0L..5L){
            list.add(PhotoAnalyzeData(i,bitmap,false))
        }
       val newList =  list.toTypedArray()
        viewModel.setPhotoData(list)
        binding.btnUploadImage.setOnClickListener {
           val action = PhotoUploadFragmentDirections.actionPhotoUploadFragmentToPhotoAnalyzeFragment(newList)
          findNavController().navigate(action)
        }

    }

    fun RealTimeData(){
        lifecycleScope.launch {
            while (true){
                delay(1000)
                viewModel.randomData((0..20).random().toInt())
            }
        }
    }
    fun setGlideImageUrl(urlList:ArrayList<String>){
        urlList.forEach { imageUrl->
            Glide.with(this).asBitmap().load(imageUrl)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        getBitmap(bitmap = resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
        }
    }


}


