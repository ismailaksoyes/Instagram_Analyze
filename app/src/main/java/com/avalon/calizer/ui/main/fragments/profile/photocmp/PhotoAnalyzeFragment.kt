package com.avalon.calizer.ui.main.fragments.profile.photocmp

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentPhotoAnalyzeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PhotoAnalyzeFragment : Fragment() {
    private lateinit var binding: FragmentPhotoAnalyzeBinding

    @Inject
    lateinit var viewModel: PhotoAnalyzeViewModel

    private lateinit var data:ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoAnalyzeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
           data = it.getStringArrayList("data") as ArrayList<String>

        }
        data.forEach {
            Log.d("Aaaa",it.toString())
        }
        lifecycleScope.launchWhenStarted {
            viewModel.photoAnalyzeData.collect { data->
                when(data){
                    is PhotoAnalyzeViewModel.PhotoState.Success->{
                        Log.d("DataPhoto",data.toString())
                    }
                    is PhotoAnalyzeViewModel.PhotoState.Random->{
                        Log.d("DataPhoto",data.data.toString())
                    }
                    else->{
                        Log.d("DataPhoto","else")
                    }
                }
            }

        }

    }

    fun getWidthAndHeightQuality(image: Bitmap): Boolean {
        val width = image.width
        val height = image.height
        return width >= 1080 && height >= 1080
    }


}