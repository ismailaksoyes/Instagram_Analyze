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
import androidx.core.os.bundleOf

import androidx.core.view.isVisible

import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.data.local.TutorialData
import com.avalon.calizer.databinding.FragmentTutorialBinding
import com.avalon.calizer.databinding.FragmentViewPagerBinding
import com.avalon.calizer.utils.MySharedPreferences
import javax.inject.Inject


class TutorialFragment : Fragment() {

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"

        fun getInstance(position: Int) = TutorialFragment().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }

    }

    private lateinit var binding: FragmentTutorialBinding
    @Inject
    lateinit var prefs: MySharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = requireArguments().getInt(ARG_POSITION)
        val getData = getTutorialData()
        with(binding){
            tvIntroDesc.text = getData[position].descText
            ivIntroView.setImageResource(getData[position].drawable)
        }
    }




    private fun getTutorialData():ArrayList<TutorialData>{
        val dataList = ArrayList<TutorialData>()
        dataList.add(TutorialData(R.drawable.ic_tutorial1_ico,"Test1"))
        dataList.add(TutorialData(R.drawable.ic_tutorial2_ico,"Test2"))
        dataList.add(TutorialData(R.drawable.ic_tutorial3_ico,"Test3"))
        //notifyDataSetChanged()
        return dataList
    }
    //this svg error gra. line




}

