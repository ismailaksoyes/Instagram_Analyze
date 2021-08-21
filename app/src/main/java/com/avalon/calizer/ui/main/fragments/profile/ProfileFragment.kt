package com.avalon.calizer.ui.main.fragments.profile

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.databinding.ProfileFragmentBinding
import com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager.FaceAnalyzeManager
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.analyzeTextColor
import com.avalon.calizer.utils.isShimmerEnabled
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
    lateinit var faceAnalyzeManager: FaceAnalyzeManager


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
        viewModel.getUserDetails(prefs.selectedAccount)
        binding.clPhotoAnalyze.setOnClickListener {
            it.findNavController().navigate(R.id.action_destination_profile_to_photoUploadFragment)
        }
        binding.clGoAccounts.setOnClickListener {
            it.findNavController().navigate(R.id.action_destination_profile_to_destination_accounts)
        }

    }

    private fun observeUserFlow() {
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

    private fun observeUserProfileData() {

        lifecycleScope.launchWhenStarted {
            viewModel.userModel.collect { userData ->
                generateUrlToBitmap(userData.profilePic)
                binding.viewmodel = viewModel
            }
        }

    }

    private fun generateUrlToBitmap(photoUrl: String?) {
        photoUrl?.let { itUrl ->
            Glide.with(this)
                .asBitmap()
                .load(itUrl)
                .into(object : CustomTarget<Bitmap>() {

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        getFaceScore(resource)
                    }
                })
        }

    }

    fun getFaceScore(bitmap: Bitmap) {
        faceAnalyzeManager.setFaceAnalyzeBitmap(bitmap)
        lifecycleScope.launchWhenCreated {
            faceAnalyzeManager.faceAnalyze.collectLatest { itFaceState ->
                when (itFaceState) {
                    is FaceAnalyzeManager.FaceAnalyzeState.Loading -> {
                        binding.tvFaceOdds.isShimmerEnabled(true)
                    }
                    is FaceAnalyzeManager.FaceAnalyzeState.Success -> {
                        binding.tvFaceOdds.isShimmerEnabled(false)
                        binding.tvFaceOdds.analyzeTextColor(itFaceState.score)
                        binding.tvFaceOdds.text = "${itFaceState.score}%"
                    }
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeUserProfileData()
        observeUserFlow()


    }


}


