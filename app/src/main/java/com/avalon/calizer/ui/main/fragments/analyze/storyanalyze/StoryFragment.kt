package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.avalon.calizer.utils.LoadingAnim
import com.avalon.calizer.utils.NavDataType
import com.avalon.calizer.utils.NavDataType.USER_PK_HIGHLIGHTS
import com.avalon.calizer.utils.NavDataType.USER_PK_TYPE


@AndroidEntryPoint
class StoryFragment : Fragment() {


    lateinit var binding: FragmentStoryBinding

    @Inject
    lateinit var viewModel: StoryViewModel

    lateinit var storyAdapter: StoryAdapter

    lateinit var loadingAnim: LoadingAnim


    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryBinding.inflate(inflater, container, false)
        loadingAnim = LoadingAnim(childFragmentManager)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(view.context)
        binding.rcStoryView.layoutManager = layoutManager
        setupRecyclerview()
        initData()
        observeStoryData()
        observeUserStoryPk()
        observeUserHighlightPk()

        binding.clUsernameStory.setOnClickListener {
            val action = StoryFragmentDirections.actionStoryFragmentToStoryBottomSheet()
            findNavController().navigate(action)

        }
        binding.clUsernameHighlights.setOnClickListener {
            val action = StoryFragmentDirections.actionStoryFragmentToHighlightsBottomSheet()
            findNavController().navigate(action)
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
                    is StoryViewModel.StoryState.OpenStory -> {
                        viewModel.setLoadingState(false)
                        setStoryViews(it.urlList)
                    }
                    is StoryViewModel.StoryState.ClickItem -> {
                        viewModel.setLoadingState(true)
                        getStory(it.userId)
                    }
                    is StoryViewModel.StoryState.Loading -> {
                        isLoadingDialog(it.isLoading)
                    }
                    is StoryViewModel.StoryState.Error -> {
                        Toast.makeText(requireContext(),"HIKAYE YOK",Toast.LENGTH_SHORT).show()
                        viewModel.setLoadingState(false)

                    }
                }

            }
        }
    }

    private fun observeUserStoryPk() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(USER_PK_TYPE)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                lifecycleScope.launch {
                    viewModel.setLoadingState(true)
                    viewModel.getStory(result)
                }
            }
    }
    private fun observeUserHighlightPk() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(USER_PK_HIGHLIGHTS)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                lifecycleScope.launch {
                    viewModel.setLoadingState(true)
                    viewModel.getStory(result)
                }
            }
    }

    private fun setStoryViews(urlList: List<String>) {
        if (urlList.isNotEmpty()) {
            val action =
                StoryFragmentDirections.actionStoryFragmentToStoryViewsFragment(urlList.toTypedArray())
            findNavController().navigate(action)
        }
    }

    private fun setAdapterStory(storyData: List<StoryData>) {
        storyAdapter.setStoryData(storyData)
    }

    private fun getStory(userId: Long) {
        lifecycleScope.launch {
            viewModel.getStory(userId)
        }
    }


    private fun initData() {
        lifecycleScope.launch {
            viewModel.getStoryList()
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