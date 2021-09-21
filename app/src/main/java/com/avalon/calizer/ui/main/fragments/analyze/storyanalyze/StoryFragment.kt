package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.databinding.FragmentStoryBinding
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.ShowStoryInterface
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.StoryAdapter
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.dialog.StoryBottomSheet
import com.avalon.calizer.utils.safeNavigate
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.content.ContextCompat.getSystemService




@AndroidEntryPoint
class StoryFragment : Fragment() {


    lateinit var binding: FragmentStoryBinding

    @Inject
    lateinit var viewModel: StoryViewModel

    lateinit var storyAdapter:StoryAdapter



    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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

        binding.clUsernameStory.setOnClickListener {
            val modalBottomSheet  = StoryBottomSheet()

           modalBottomSheet.show(childFragmentManager, StoryBottomSheet.TAG)

        }


    }

    private fun setupRecyclerview() {
        storyAdapter = StoryAdapter(viewModel)
        binding.rcStoryView.adapter = storyAdapter
        binding.rcStoryView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )

    }

    private fun observeStoryData() {
        lifecycleScope.launch {
            viewModel.storyData.collectLatest {
                when (it) {
                    is StoryViewModel.StoryState.Success -> {
                        setAdapterStory(it.storyData)
                    }
                    is StoryViewModel.StoryState.OpenStory->{
                       setStoryViews(it.urlList)
                    }
                    is StoryViewModel.StoryState.ClickItem->{
                        getStory(it.userId)
                    }
                    is StoryViewModel.StoryState.Error -> {

                    }
                }

            }
        }
    }

    private  fun setStoryViews(urlList: List<String>){
        if (urlList.isNotEmpty()){
            val action = StoryFragmentDirections.actionStoryFragmentToStoryViewsFragment(urlList.toTypedArray())
            findNavController().navigate(action)
        }
    }

    private fun setAdapterStory(storyData: List<StoryData>) {
        storyAdapter.setStoryData(storyData)
    }

    private fun getStory(userId:Long) {
        lifecycleScope.launch(Dispatchers.IO) {
          viewModel.getStory(userId)
        }
    }



    override fun onPause() {
        super.onPause()
    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.getStoryList()
        }

    }

}