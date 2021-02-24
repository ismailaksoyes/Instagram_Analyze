package com.avalon.calizer.ui.tutorial

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentTutorialBinding
import com.avalon.calizer.databinding.ProfileFragmentBinding
import com.avalon.calizer.ui.accounts.adapters.AccountsAdapter

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
    fun viewPagerControl(){
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d("position","$position offset $positionOffset")
                when(position){
                    0->{
                        if(!binding.tvNextIntro.isVisible){

                           // binding.tvNextIntro.visibility = View.VISIBLE
                            binding.tvNextIntro.visible()

                        }
                        if(!binding.tvIntroDesc.isVisible){
                            binding.tvIntroDesc.visibility = View.VISIBLE
                        }
                    }
                    1-> {
                        if(binding.tvNextIntro.isVisible){
                          //  binding.tvNextIntro.visibility = View.GONE
                            binding.tvNextIntro.gone()
                        }
                    }
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }
    fun TextView.visible(){
        visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, 20f, 0f)
        animate.duration = 700
       // animate.fillAfter = true
        this.startAnimation(animate)
        animate.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                alpha = 0f
            }

            override fun onAnimationEnd(animation: Animation?) {
                alpha = 1f
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

    }
    fun TextView.gone(){
        //visibility = View.GONE
        val animate = TranslateAnimation(0f, 0f, 0f,20f)
        animate.duration = 500
       // animate.fillAfter = true
        this.startAnimation(animate)
        animate.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                alpha = 1f
            }

            override fun onAnimationEnd(animation: Animation?) {
                  post(Runnable { visibility = View.GONE })

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })


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

