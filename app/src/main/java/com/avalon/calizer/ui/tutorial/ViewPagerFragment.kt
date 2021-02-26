package com.avalon.calizer.ui.tutorial

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentTutorialBinding
import com.avalon.calizer.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentViewPagerBinding
    private val tutorialAdapter by lazy { TutorialPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager = binding.viewPager2
        viewPager.adapter = tutorialAdapter
      //  viewPagerControl()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }


}