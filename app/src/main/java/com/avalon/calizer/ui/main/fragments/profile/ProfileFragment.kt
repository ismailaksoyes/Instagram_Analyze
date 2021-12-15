package com.avalon.calizer.ui.main.fragments.profile

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.databinding.ProfileFragmentBinding
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager.FaceAnalyzeManager
import com.avalon.calizer.ui.main.fragments.profile.photocmp.photopager.PoseAnalyzeManager
import com.avalon.calizer.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>(ProfileFragmentBinding::inflate) {


    val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var faceAnalyzeManager: FaceAnalyzeManager

    @Inject
    lateinit var poseAnalyzeManager: PoseAnalyzeManager


    @Inject
    lateinit var prefs: MySharedPreferences


    private fun initData() {
        lifecycleScope.launch {
            viewModel.setUserDetailsLoading()
            viewModel.getUserDetails()
            viewModel.getFollowersCount()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        observeUserFlow()
       // observeProfilePhotoAnalyze()
        test3()
    }

    fun test3(){
        lifecycleScope.launch {
            viewModel.poseScore2.collect {
                val score = "${it}%"
                binding.tvFaceOdds.text = score
                binding.tvFaceOdds.isShimmerEnabled(false)
                binding.tvFaceOdds.analyzeTextColor(it)
            }
        }
    }



    private fun observeUserFlow() {
        lifecycleScope.launch {
            viewModel.userData.collectLatest {
                when (it) {
                    is ProfileViewModel.UserDataFlow.Loading -> {
                        binding.tvProfileFollowers.isShimmerEnabled(true)
                        binding.tvProfileFollowing.isShimmerEnabled(true)
                        binding.tvProfilePosts.isShimmerEnabled(true)
                        binding.tvProfileUsername.isShimmerEnabled(true)
                    }
                    is ProfileViewModel.UserDataFlow.GetUserDetails -> {
                        Log.d("OBSERVETEST", "observeUserFlow: ")
                        viewModel.setViewUserData(it.accountsInfoData)
                        createProfilePhoto(it.accountsInfoData.profilePic)
                        binding.tvProfileFollowers.isShimmerEnabled(false)
                        binding.tvProfileFollowing.isShimmerEnabled(false)
                        binding.tvProfilePosts.isShimmerEnabled(false)
                        binding.tvProfileUsername.isShimmerEnabled(false)
                    }

                    is ProfileViewModel.UserDataFlow.Error -> {
                        val action =
                            ProfileFragmentDirections.actionDestinationProfileToDestinationAccounts()
                        findNavController().navigate(action)
                    }


                }


            }
        }
    }

    private fun createProfilePhoto(profileUrl: String?) {
        profileUrl?.let { itProfileUrl ->
            generateUrlToBitmap(itProfileUrl)
            Log.d("OBSERVETEST", "GENERATEURLTOBITMAP: ")
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
                        Log.d("OBSERVETEST", "ONRESOURCEREADY: ")
                        getFaceScore(resource)
                        getPoseScore(resource)
                    }
                })
        }

    }

    fun getFaceScore(bitmap: Bitmap) {
        faceAnalyzeManager.setFaceAnalyzeBitmap(bitmap)
        binding.tvFaceOdds.isShimmerEnabled(true)
        faceAnalyzeManager.result = {
            viewModel.setFaceScore(it)
        }

    }

    fun observeProfilePhotoAnalyze() {
        observeLive(viewModel.poseScore) {
            val score = "${it}%"
            binding.tvPozeOdds.text = score
            binding.tvPozeOdds.isShimmerEnabled(false)
            binding.tvPozeOdds.analyzeTextColor(it)
        }
        observeLive(viewModel.faceScore) {
            val score = "${it}%"
            binding.tvFaceOdds.text = score
            binding.tvFaceOdds.isShimmerEnabled(false)
            binding.tvFaceOdds.analyzeTextColor(it)
        }
    }

    fun getPoseScore(bitmap: Bitmap) {
        poseAnalyzeManager.setBodyAnalyzeBitmap(bitmap)
        lifecycleScope.launchWhenStarted {
            poseAnalyzeManager.bodyAnalyze.collect { itPoseState ->
                when (itPoseState) {
                    is PoseAnalyzeManager.BodyAnalyzeState.Loading -> {
                        binding.tvPozeOdds.isShimmerEnabled(true)
                    }
                    is PoseAnalyzeManager.BodyAnalyzeState.Success -> {
                        viewModel.setPoseScore(itPoseState.score)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        navigateEvent()
        //observeProfilePhotoAnalyze()
    }




    private fun navigateEvent() {
        binding.clGoAccounts.setOnClickListener {
            val action = ProfileFragmentDirections.actionDestinationProfileToDestinationAccounts()
            it.findNavController().navigate(action)
        }
        binding.clPhotoAnalyze.setOnClickListener {
            val action = ProfileFragmentDirections.actionDestinationProfileToPhotoUploadFragment()
            it.findNavController().navigate(action)
        }

    }



}


