package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allposts.dialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.avalon.calizer.R
import com.avalon.calizer.databinding.BottomSheetHighlightsStoryBinding
import com.avalon.calizer.databinding.MostLikedDialogBinding
import com.avalon.calizer.utils.LoadingAnim
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MostLikedDialog : BottomSheetDialogFragment() {

    lateinit var binding:MostLikedDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
       // loadingAnim = LoadingAnim(childFragmentManager)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MostLikedDialogBinding.inflate(inflater, container, false)
        return binding.root

    }
}