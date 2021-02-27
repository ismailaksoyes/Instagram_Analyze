package com.avalon.calizer.ui.tutorial


import androidx.fragment.app.Fragment



import androidx.viewpager2.adapter.FragmentStateAdapter


class TutorialPagerAdapter(fragment:Fragment):
    FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int {
       return 3
    }

    override fun createFragment(position: Int): Fragment {
      return TutorialFragment.getInstance(position)
    }

}