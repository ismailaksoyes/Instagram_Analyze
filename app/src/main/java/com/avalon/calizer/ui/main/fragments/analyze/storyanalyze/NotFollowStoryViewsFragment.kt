package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.components.AppTopBar
import com.avalon.calizer.components.StoryListContent
import com.avalon.calizer.ui.theme.Ps4Theme
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setContent {
                Ps4Theme() {
                    Column() {
                        AppTopBar(viewModel = viewModel)
                        Spacer(modifier = Modifier.height(5.dp))
                        StoryListContent(viewModel = viewModel)
                    }
                }

            }


        }
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