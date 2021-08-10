package com.avalon.calizer.ui.main.fragments.profile

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.databinding.ProfileFragmentBinding
import com.avalon.calizer.utils.MySharedPreferences

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


@AndroidEntryPoint
class ProfileFragment : Fragment() {


    @Inject
    lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding


    @Inject
    lateinit var prefs: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    fun initData() {
        lifecycleScope.launchWhenStarted {
            viewModel.userData.collect {
                when (it) {
                    is ProfileViewModel.UserDataFlow.Empty -> {

                    }
                    is ProfileViewModel.UserDataFlow.GetUserDetails -> {

                    }


                }


            }
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clPhotoAnalyze.setOnClickListener {
            it.findNavController().navigate(R.id.action_destination_profile_to_photoUploadFragment)
        }
        val imageUrl: String = "https://thispersondoesnotexist.com/image"

        viewModel.getUserDetails(prefs.selectedAccount)

        binding.clGoAccounts.setOnClickListener {
            it.findNavController().navigate(R.id.action_destination_profile_to_destination_accounts)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userModel.collect {
                Log.d("datatest", "$it")
                binding.viewmodel = viewModel
            }
        }


//        Glide.with(this)
//            .asBitmap()
//            .load(imageUrl)
//            .into(object : SimpleTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    runImageFaceDetector(resource)
//
//                }
//            })
    }


}


