package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.utils.LoadingAnim
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotFollowStoryViewsFragment : Fragment() {


    val viewModel: NotFollowStoryViewsViewModel  by viewModels()

    private lateinit var loadingAnim: LoadingAnim


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingAnim = LoadingAnim(childFragmentManager)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeStoryViewer()
    }

    fun initData() {
        viewModel.setLoadingState()
        getFollowers()

    }

    fun getFollowers() {
        lifecycleScope.launch {
            viewModel.getFollowers()
        }
    }



    fun observeStoryViewer() {
        lifecycleScope.launch {
            viewModel.storyViewData.collect {
                when (it) {
                    is NotFollowStoryViewsViewModel.NotStoryState.StoryList -> {
                        it.storyList.forEach { itFor ->
                            itFor.storyId?.let { itStoryId ->
                                viewModel.getStoryViewer(storyId = itStoryId)
                            }

                        }
                    }
                    is NotFollowStoryViewsViewModel.NotStoryState.Navigate->{
                        val action = NotFollowStoryViewsFragmentDirections.actionNotFollowStoryViewsFragmentToStoryFragment()
                        findNavController().navigate(action)
                    }
                    is NotFollowStoryViewsViewModel.NotStoryState.Loading->{
                        isLoadingDialog(true)
                    }
                    is NotFollowStoryViewsViewModel.NotStoryState.Success->{
                        isLoadingDialog(false)
                    }
                    is NotFollowStoryViewsViewModel.NotStoryState.Error->{
                        isLoadingDialog(false)
                    }

                }
            }
        }
    }
    private fun isLoadingDialog(isStatus: Boolean) {
        if (isStatus) {
            loadingAnim.showDialog()
        } else {
            loadingAnim.closeDialog()
        }
    }



}