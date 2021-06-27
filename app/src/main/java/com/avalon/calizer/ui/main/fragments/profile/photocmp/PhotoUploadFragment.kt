package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoUploadBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
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
        urlList.add("https://img.piri.net/mnresize/900/-/resim/upload/2017/11/12/11/28/12ee7246kapak.jpg")
        setGlideImageUrl(urlList)
        RealTimeData()
    }

    fun getBitmap(bitmap: Bitmap){
        val data = PhotoAnalyzeData(0,bitmap,false)
        Log.d("DataPhoto",data.toString())
        val list = ArrayList<PhotoAnalyzeData>()
        list.add(data)
        val testList = ArrayList<String>()
        testList.add("type1")
        testList.add("type2")
        testList.add("type3")

        viewModel.setPhotoData(list)
        binding.btnUploadImage.setOnClickListener {
            val bundle = bundleOf("data" to testList)
           findNavController().navigate(R.id.action_photoUploadFragment_to_photoAnalyzeFragment,bundle)
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


