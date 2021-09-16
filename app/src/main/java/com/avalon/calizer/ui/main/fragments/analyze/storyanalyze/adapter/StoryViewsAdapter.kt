package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.StoryShowFragment
import com.avalon.calizer.ui.tutorial.TutorialFragment

class StoryViewsAdapter(fragment:Fragment,val urlList: List<String>):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return urlList.size
    }

    override fun createFragment(position: Int): Fragment {
        return StoryShowFragment.getInstance(urlList[position])
    }

}