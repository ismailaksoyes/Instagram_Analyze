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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TutorialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TutorialFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentTutorialBinding
    private val tutorialAdapter by lazy { TutorialPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewPager = binding.viewPager2
        viewPager.adapter = tutorialAdapter
        viewPagerControl()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTutorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun viewPagerControl() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d("position", "$position offset $positionOffset")
                when (position) {
                    0 -> {
                        if (!binding.tvNextIntro.isVisible) {

                            // binding.tvNextIntro.visibility = View.VISIBLE
                            binding.tvNextIntro.visible()

                        }
                        if (!binding.tvIntroDesc.isVisible) {
                            binding.tvIntroDesc.visibility = View.VISIBLE
                        }
                        if(!binding.clStartNowNext.isVisible){
                            binding.clStartNowNext.visibility = View.VISIBLE
                            binding.ivStartNowImage.visibility = View.VISIBLE
                            binding.tvStartNow.visibility = View.VISIBLE
                        }
                    }
                    1 -> {
                        if (binding.tvNextIntro.isVisible) {
                            //  binding.tvNextIntro.visibility = View.GONE
                            binding.tvNextIntro.gone()
                        }
                    }
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }


    fun TextView.visible() {
        visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.fade_in)
        this.startAnimation(animation)

    }

    fun TextView.gone() {
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.fade_out)
        this.startAnimation(animation)
        postDelayed({
            visibility = View.GONE
        }, 500)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TutorialFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

