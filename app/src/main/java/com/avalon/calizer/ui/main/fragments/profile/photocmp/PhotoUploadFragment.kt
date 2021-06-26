package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avalon.calizer.R
import com.avalon.calizer.data.local.profile.PhotoAnalyzeData
import com.avalon.calizer.databinding.FragmentPhotoUploadBinding
import com.bumptech.glide.Glide


class PhotoUploadFragment : Fragment() {
    private lateinit var binding:FragmentPhotoUploadBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoUploadBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmap = Glide.with(this).asBitmap().load("https://img.piri.net/mnresize/900/-/resim/upload/2017/11/12/11/28/12ee7246kapak.jpg")
        val data = PhotoAnalyzeData(0,bitmap,)
    }


}