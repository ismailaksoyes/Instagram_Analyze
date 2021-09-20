package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.avalon.calizer.R
import com.avalon.calizer.databinding.BottomSheetInputStoryBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject
import androidx.fragment.app.FragmentActivity
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.StoryViewModel
import com.avalon.calizer.utils.Keyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetInputStoryBinding

    @Inject
    lateinit var viewModel: StoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetInputStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       Keyboard.show(view)


    }


    override fun onDestroy() {
        super.onDestroy()


    }

    private fun getUserPk(){
      val usernameEdit= binding.etInput.text
        if (!usernameEdit.isNullOrEmpty()){
            val username = usernameEdit.trim()
            viewModel.get
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
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.skipCollapsed = true
    }
}