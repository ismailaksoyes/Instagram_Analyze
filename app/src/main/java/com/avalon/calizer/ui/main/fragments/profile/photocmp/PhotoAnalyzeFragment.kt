package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding


class PhotoAnalyzeFragment : Fragment() {
        private lateinit var binding: FragmentPhotoAnalyzeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoAnalyzeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun getWidthAndHeightQuality(image:Bitmap):Boolean{
        val width = image.width
        val height = image.height
        return width>=1080&&height>=1080
    }


}