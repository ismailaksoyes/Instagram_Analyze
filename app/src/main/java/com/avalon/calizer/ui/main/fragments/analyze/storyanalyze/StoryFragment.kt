package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentStoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StoryFragment : Fragment() {

    lateinit var binding: FragmentStoryBinding

    @Inject
    lateinit var viewModel: StoryViewModel

    private val storyAdapter by lazy { StoryAdapter() }
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentStoryBinding.inflate(inflater,container,false)
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
    private fun observeStoryData(){
        lifecycleScope.launch {
            viewModel.storyData.collect {
            when(it){
                is StoryViewModel.StoryState.Success->{
                    it.storyData.forEach { itData->
                        storyAdapter.setStoryData(it.storyData)
                        Log.d("STORYSUCCESS","${itData}")
                    }
                }
                is StoryViewModel.StoryState.Error->{

                }
            }

            }
        }
    }
    private fun initData(){
        lifecycleScope.launch {
            viewModel.getStoryList()
        }

    }

}