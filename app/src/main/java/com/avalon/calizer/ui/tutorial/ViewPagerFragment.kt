package com.avalon.calizer.ui.tutorial

import android.animation.ValueAnimator
import android.app.ActionBar
import android.os.Bundle
import android.service.autofill.FieldClassification
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentTutorialBinding
import com.avalon.calizer.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentViewPagerBinding
    private val tutorialAdapter by lazy { TutorialPagerAdapter(this) }



    private var onTutorialPageChangeCallBack= object  : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            getTutorialAnim(position)
            Log.d("position","ccc $position")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager2
        viewPager.orientation - ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = tutorialAdapter
        viewPager.registerOnPageChangeCallback(onTutorialPageChangeCallBack)

    }

    override fun onDestroy() {
        binding.viewPager2.unregisterOnPageChangeCallback(onTutorialPageChangeCallBack)
        super.onDestroy()
    }

    fun getTutorialAnim(position:Int){
        when(position){
            0 -> {
            if(!binding.tvNextIntro.isVisible){
                binding.tvNextIntro.textAnimVisible()
            }
               // binding.tvStartNow.visibility = View.GONE
                binding.tvStartNow.visibility = View.GONE
                binding.clStartNowNext.layoutParams.width = 0


            }
            1 -> {
                if(binding.tvNextIntro.isVisible){
                    binding.tvNextIntro.textAnimGone()
                }

            }
            2->{
                binding.tvStartNow.visibility = View.VISIBLE

                    binding.clStartNowNext.openAnim()



            }


        }
    }
    fun ConstraintLayout.openAnim(){
        post {
            val mes = Constraints.LayoutParams.MATCH_PARENT
            val anim = ValueAnimator.ofInt(0,350)
            anim.duration = 300
            anim.addUpdateListener {
                Log.d("anim","${it.animatedValue}")
                this.layoutParams.width = it.animatedValue as Int
            }
            anim.start()

        }

    }
    fun TextView.textAnimVisible(){
        visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(this.context,R.anim.fade_in)
        this.startAnimation(anim)
    }
    fun TextView.textAnimGone(){
        val anim = AnimationUtils.loadAnimation(this.context,R.anim.fade_out)
        this.startAnimation(anim)
        postDelayed({
            visibility = View.INVISIBLE
        },500)
    }



}