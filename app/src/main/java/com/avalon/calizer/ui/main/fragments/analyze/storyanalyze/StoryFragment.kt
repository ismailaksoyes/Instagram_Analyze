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
import androidx.fragment.app.viewModels
import com.avalon.calizer.shared.localization.LocalizationManager
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.utils.LoadingAnim
import com.avalon.calizer.utils.NavDataType
import com.avalon.calizer.utils.NavDataType.USER_PK_HIGHLIGHTS
import com.avalon.calizer.utils.NavDataType.USER_PK_TYPE


@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>(FragmentStoryBinding::inflate) {


    val viewModel: StoryViewModel by viewModels()


    lateinit var loadingAnim: LoadingAnim


    private lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var localizationManager: LocalizationManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(view.context)
        binding.localization = localizationManager
        binding.rcStoryView.layoutManager = layoutManager
        loadingAnim = LoadingAnim(childFragmentManager)
        setupRecyclerview()
        observeUserStoryPk()
        observeUserHighlightPk()
        actionNavigate()
        observeLoadingState()
        observeOpenStoryData()
        observeStoryData()
    }


    private fun setupRecyclerview() {
        binding.rcStoryView.adapter = StoryAdapter { itStoryId ->
            viewModel.setLoadingState(true)
            getStory(itStoryId)
        }
        binding.rcStoryView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )

    }

    private fun observeOpenStoryData(){
        lifecycleScope.launch {
            viewModel.openStory.collectLatest {
                when(it){
                    is StoryViewModel.OpenStoryState.Success->{
                        viewModel.setLoadingState(false)
                        setStoryViews(it.storyList)
                    }
                    is StoryViewModel.OpenStoryState.Error->{
                        Toast.makeText(requireContext(), "HIKAYE YOK", Toast.LENGTH_SHORT).show()
                        viewModel.setLoadingState(false)
                    }
                }


            }
        }
    }

    private fun observeLoadingState(){
        lifecycleScope.launch {
            viewModel.loading.collectLatest {
                isLoadingDialog(it)
            }
        }
    }

    private fun observeStoryData() {
        lifecycleScope.launch {
            viewModel.storyList.collect {
                when (it) {
                    is StoryViewModel.StoryState.Success -> {
                        setAdapterStory(it.storyData)
                    }
                    is StoryViewModel.StoryState.Error -> {
                        Toast.makeText(requireContext(), "HIKAYE YOK", Toast.LENGTH_SHORT).show()
                      //  viewModel.setLoadingState(false)
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
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            USER_PK_HIGHLIGHTS
        )
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                lifecycleScope.launch {
                    viewModel.setLoadingState(true)
                    viewModel.getHighlightsStory(result)
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
        (binding.rcStoryView.adapter as StoryAdapter).setStoryData(storyData)
    }

    private fun getStory(userId: Long) {
        lifecycleScope.launch {
            viewModel.getStory(userId)
        }
    }

    private fun isLoadingDialog(isStatus: Boolean) {
        if (isStatus) {
            loadingAnim.showDialog()
        } else {
            loadingAnim.closeDialog()
        }
    }

    private fun actionNavigate() {
        binding.clUsernameStory.setOnClickListener {
            val action = StoryFragmentDirections.actionStoryFragmentToStoryBottomSheet()
            findNavController().navigate(action)

        }
        binding.clUsernameHighlights.setOnClickListener {
            val action = StoryFragmentDirections.actionStoryFragmentToHighlightsBottomSheet()
            findNavController().navigate(action)
        }
        binding.clNoFollowStory.setOnClickListener {
            val action = StoryFragmentDirections.actionStoryFragmentToNotFollowStoryViewsFragment()
            findNavController().navigate(action)
        }
        binding.toolbar.onBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}