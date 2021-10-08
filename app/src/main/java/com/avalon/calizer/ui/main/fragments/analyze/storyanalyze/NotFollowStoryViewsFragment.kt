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
import androidx.lifecycle.lifecycleScope
import com.avalon.calizer.R
import com.avalon.calizer.components.AppTopBar
import com.avalon.calizer.components.StoryListContent
import com.avalon.calizer.ui.theme.Ps4Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotFollowStoryViewsFragment : Fragment() {


    @Inject
    lateinit var viewModel: NotFollowStoryViewsViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       return ComposeView(requireContext()).apply {
           setContent {
               Ps4Theme() {
                   Column() {
                       AppTopBar()
                       Spacer(modifier =Modifier.height(5.dp) )
                       StoryListContent()
                   }
               }

           }
       }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observeFollowers()
        observeStoryViewer()
    }

    fun initData(){
        viewModel.setLoadingState()
        getFollowers()
        lifecycleScope.launch {
            viewModel.getStoryId()
        }

    }

    fun getFollowers(){
        lifecycleScope.launch {
            viewModel.getFollowers()
        }
    }
    fun observeFollowers(){
        lifecycleScope.launch {
            viewModel.followersData.collect {
                when(it){
                    is NotFollowStoryViewsViewModel.FollowersState.FollowersList->{

                    }
                }
            }
        }
    }

    fun observeStoryViewer(){
        lifecycleScope.launch {
            viewModel.storyViewData.collect {
                when(it){
                    is NotFollowStoryViewsViewModel.NotStoryState.StoryList->{
                        it.storyList.forEach { itFor->
                            itFor.storyId?.let { itStoryId->
                                viewModel.getStoryViewer(itStoryId)
                            }

                        }
                    }
                    is NotFollowStoryViewsViewModel.NotStoryState.StoryViewerList->{
                        it.storyViewer.forEach { itFor->
                            Log.d("STORYVIEWER",itFor.toString())
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TestFun(text1:String){
        Text("TEST DENEME $text1")
    }

    @Preview(showBackground = true, uiMode = 1, showSystemUi = true)
    @Composable
    fun DefaultPreview(){
        Column() {
            AppTopBar()
            Spacer(modifier =Modifier.height(5.dp) )
            StoryListContent()
        }

    }



}