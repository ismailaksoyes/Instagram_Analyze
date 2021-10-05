package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentNotFollowStoryViewsBinding
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
                   TestFun(text1 = "dsfdsfsd")
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
                                viewModel.getStoryViewer(itFor.storyId)
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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview(){
        TestFun("sdfsd")
    }



}