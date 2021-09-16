package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentStoryShowBinding
import com.avalon.calizer.ui.tutorial.TutorialFragment


class StoryShowFragment : Fragment() {

    lateinit var binding: FragmentStoryShowBinding

    companion object {
        private const val ARG_URL = "ARG_URL"

        fun getInstance(url: String) = StoryFragment().apply {
            arguments = bundleOf(ARG_URL to url)
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryShowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val test = arguments.toString()
    }


}