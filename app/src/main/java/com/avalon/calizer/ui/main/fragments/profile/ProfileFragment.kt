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
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.databinding.ProfileFragmentBinding
import com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager.FaceAnalyzeManager
import com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager.PoseAnalyzeManager
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    lateinit var poseAnalyzeManager: PoseAnalyzeManager


    @Inject
    lateinit var prefs: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    private fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.setUserDetailsLoading()
             viewModel.getUserDetails()
        }

    }

    private fun observeNavigateFlow(){
        lifecycleScope.launch {
            viewModel.navigateFlow.collect {
                when(it){
                    is ProfileViewModel.NavigateFlow.AccountsPage->{
                        view?.findNavController()?.navigate(R.id.action_destination_profile_to_destination_accounts)
                    }
                    is ProfileViewModel.NavigateFlow.PhotoAnalyze->{
                        view?.findNavController()?.navigate(R.id.action_destination_profile_to_photoUploadFragment)
                    }
                }
            }
        }
    }

    private fun observeUserFlow() {
        lifecycleScope.launch {
            viewModel.userData.collect {
                when (it) {
                    is ProfileViewModel.UserDataFlow.Empty -> {

                    }
                    is ProfileViewModel.UserDataFlow.Loading->{
                         binding.tvProfileFollowers.isShimmerEnabled(true)
                        binding.tvProfileFollowing.isShimmerEnabled(true)
                        binding.tvProfilePosts.isShimmerEnabled(true)
                        binding.tvProfileUsername.isShimmerEnabled(true)
                    }
                    is ProfileViewModel.UserDataFlow.GetUserDetails -> {
                        viewModel.setViewUserData(it.accountsInfoData)
                        createProfilePhoto(it.accountsInfoData.profilePic)
                        binding.tvProfileFollowers.isShimmerEnabled(false)
                        binding.tvProfileFollowing.isShimmerEnabled(false)
                        binding.tvProfilePosts.isShimmerEnabled(false)
                        binding.tvProfileUsername.isShimmerEnabled(false)
                        binding.viewmodel = viewModel
                    }


                }


            }
        }
    }

    private fun createProfilePhoto(profileUrl:String?){
        profileUrl?.let { itProfileUrl->
            generateUrlToBitmap(itProfileUrl)
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
                        getPoseScore(resource)
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

    fun getPoseScore(bitmap: Bitmap) {
        poseAnalyzeManager.setBodyAnalyzeBitmap(bitmap)
        lifecycleScope.launchWhenCreated {
            poseAnalyzeManager.bodyAnalyze.collectLatest { itPoseState ->
                when (itPoseState) {
                    is PoseAnalyzeManager.BodyAnalyzeState.Loading -> {
                        binding.tvPozeOdds.isShimmerEnabled(true)
                    }
                    is PoseAnalyzeManager.BodyAnalyzeState.Success -> {
                        binding.tvPozeOdds.isShimmerEnabled(false)
                        binding.tvPozeOdds.analyzeTextColor(itPoseState.score)
                        binding.tvPozeOdds.text = "${itPoseState.score}%"
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeUserFlow()
        observeNavigateFlow()

    }


}


