package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.databinding.FragmentStoryBinding
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.ShowStoryInterface
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StoryFragment : Fragment() {


    lateinit var binding: FragmentStoryBinding

    @Inject
    lateinit var viewModel: StoryViewModel

    private val storyAdapter by lazy { StoryAdapter(object : ShowStoryInterface{
        override fun openStory(userId: Long) {
            super.openStory(userId)
            getStory(userId)
        }
    }) }
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(view.context)
        binding.rcStoryView.layoutManager = layoutManager
        setupRecyclerview()
        initData()
        observeStoryData()
    }

    private fun setupRecyclerview() {
        binding.rcStoryView.adapter = storyAdapter
        binding.rcStoryView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )

    }

    private fun observeStoryData() {
        lifecycleScope.launch {
            viewModel.storyData.collect {
                when (it) {
                    is StoryViewModel.StoryState.Success -> {
                        setAdapterStory(it.storyData)
                    }
                    is StoryViewModel.StoryState.OpenStory->{
                        setStoryViews(it.urlList)
                    }
                    is StoryViewModel.StoryState.Error -> {

                    }
                }

            }
        }
    }

    private fun setStoryViews(urlList: List<String>){
        if (urlList.isNotEmpty()){
            val action = StoryFragmentDirections.actionStoryFragmentToStoryViewsFragment(urlList.toTypedArray())
            findNavController().navigate(action)
        }
    }

    private fun setAdapterStory(storyData: List<StoryData>) {
        storyAdapter.setStoryData(storyData)
    }



    private fun getStory(userId:Long) {
        lifecycleScope.launch {
           viewModel.getStory(userId)
        }
    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.getStoryList()

        }

    }

}