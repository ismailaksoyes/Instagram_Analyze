package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentStoryViewsBinding
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.StoryAdapter
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.StoryViewsAdapter
import com.avalon.calizer.ui.tutorial.TutorialFragment
import com.avalon.calizer.ui.tutorial.TutorialPagerAdapter


class StoryViewsFragment : Fragment() {
    private val args:StoryViewsFragmentArgs by navArgs()
    lateinit var binding: FragmentStoryViewsBinding
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryViewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val urlList = args.storyList
        createViewPagerAdapter(urlList.toList())
    }

    private fun createViewPagerAdapter(urlList:List<String>){
        val storyAdapter = StoryViewsAdapter(this,urlList)
        viewPager = binding.viewPager2
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = storyAdapter
        Log.d("", "createViewPagerAdapter: asdasdasd")
    }


}