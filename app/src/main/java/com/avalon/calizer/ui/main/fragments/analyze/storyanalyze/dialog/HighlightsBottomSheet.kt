package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.story.HighlightsData
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.databinding.BottomSheetHighlightsStoryBinding
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.HighlightsAdapter
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.StoryAdapter
import com.avalon.calizer.utils.Keyboard
import com.avalon.calizer.utils.LoadingAnim
import com.avalon.calizer.utils.NavDataType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HighlightsBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetHighlightsStoryBinding

    @Inject
    lateinit var viewModel: HighlightsSheetViewModel


    private lateinit var loadingAnim: LoadingAnim

    lateinit var highlightsAdapter: HighlightsAdapter

    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setStyle(STYLE_NORMAL, R.style.DialogStyle)
        loadingAnim = LoadingAnim(childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetHighlightsStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(view.context)
       // binding.rcHighlights.layoutManager = layoutManager
        binding.etInput.requestFocus()
        Keyboard.show(view)
        observeUserPk()
        showStoryClick()
        onFocusInput()

    }

    private fun showStoryClick() {
        binding.btnStoryShow.setOnClickListener {
            if (!binding.etInput.text.isNullOrEmpty()){
                viewModel.setLoadingPk()
                getUserPk()
            }else{
                binding.etInputLayout.error = "not null"
            }

        }
    }


    private fun getInputText(): String? {
        val usernameEdit = binding.etInput.text
        return if (!usernameEdit.isNullOrEmpty()) {
            usernameEdit.toString().trim()
        } else {
            null
        }
    }

    private fun getUserPk() {
        lifecycleScope.launch {
            getInputText()?.let { itUsername->
                viewModel.getUserPk(itUsername)
            }
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .also { dialog ->
                dialog.setOnShowListener {
                    val viewBehavior = BottomSheetBehavior.from<View>(
                        dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                    )
                    setupBehavior(viewBehavior)
                }
            }
    }

    private fun setupBehavior(bottomSheetBehavior: BottomSheetBehavior<View>) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.skipCollapsed = true
    }

    private fun observeUserPk() {
        lifecycleScope.launch {
            viewModel.userPkHigh.collect {
                when (it) {
                    is HighlightsSheetViewModel.UserPkState.Loading -> {
                        isLoadingDialog(true)
                    }
                    is HighlightsSheetViewModel.UserPkState.Success -> {
                        //isLoadingDialog(false)
                        viewModel.getHighlights(it.userId)

                       // openStory(it.userId)
                    }
                    is HighlightsSheetViewModel.UserPkState.Highlights->{
                        isLoadingDialog(false)
                       // binding.rcHighlights.visibility = View.VISIBLE
                        setAdapterHighlights(it.highlightsData)
                    }
                    is HighlightsSheetViewModel.UserPkState.Error -> {
                        isLoadingDialog(false)
                        noSuchUser()
                    }
                }
            }
        }
    }

    private fun openStory(userPk: Long) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(NavDataType.USER_PK_HIGHLIGHTS, userPk)
        findNavController().popBackStack()

    }
    private fun onFocusInput(){
        binding.etInput.setOnClickListener {
            binding.etInputLayout.error = null
           // binding.rcHighlights.visibility = View.GONE
        }
    }

    private fun noSuchUser() {
        binding.etInputLayout.error = "nosuchuser"
        view?.let { itView ->
            Keyboard.show(itView)
        }

    }
    private fun setAdapterHighlights(highlightsData:List<HighlightsData>) {
        highlightsAdapter.setHighlightsData(highlightsData)
    }

    private fun isLoadingDialog(isStatus: Boolean) {
        if (isStatus) {
            loadingAnim.showDialog()
        } else {
            loadingAnim.closeDialog()
        }
    }

}