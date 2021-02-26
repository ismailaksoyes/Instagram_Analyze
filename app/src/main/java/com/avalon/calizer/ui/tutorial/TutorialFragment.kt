package com.avalon.calizer.ui.tutorial

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils

import android.view.animation.TranslateAnimation
import android.widget.TextView

import androidx.core.view.isVisible

import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentTutorialBinding
import com.avalon.calizer.databinding.FragmentViewPagerBinding


class TutorialFragment : Fragment() {
    private lateinit var binding: FragmentTutorialBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorialBinding.inflate(inflater, container, false)
        return binding.root
    }




}

