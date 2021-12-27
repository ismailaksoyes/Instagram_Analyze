package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allposts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.R
import com.avalon.calizer.data.local.analyze.PostViewData
import com.avalon.calizer.databinding.FragmentAllPostBinding
import com.avalon.calizer.shared.localization.LocalizationManager
import com.avalon.calizer.shared.model.LocalizationType
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allposts.adapter.AllPostAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AllPostFragment : BaseFragment<FragmentAllPostBinding>(FragmentAllPostBinding::inflate) {



    val list1 = ArrayList<PostViewData>()
    val list2 = ArrayList<PostViewData>()

    @Inject
    lateinit var localizationManager: LocalizationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerview()
        observeTabLayout()

        binding.toolbar.setTitle = localizationManager.localization(LocalizationType.ANALYZE_ALLPOSTS_TITLE)

        if (savedInstanceState!=null){
            binding.pagerAnalyze.getTabAt(savedInstanceState.getInt("tabState"))?.select()
        }

        for (i in 41..49){
            list1.add(PostViewData(i.toLong(),54,78,"https://picsum.photos/id/$i/200/200"))
        }
        for (i in 44..52){
            list2.add(PostViewData(i.toLong(),57,79,"https://picsum.photos/id/$i/200/200"))
        }

    }

    private fun setupRecyclerview() {
        binding.rcMostLikedPost.adapter = AllPostAdapter()
        binding.rcMostLikedPost.layoutManager = GridLayoutManager(
            this.context,
            3,
            RecyclerView.VERTICAL,
            false
        )
        binding.rcMostLikedPost.setHasFixedSize(true)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("tabState",getSelectedTab())
    }

    private fun getSelectedTab():Int{
        return binding.pagerAnalyze.selectedTabPosition
    }

    private fun observeTabLayout(){
        binding.pagerAnalyze.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("tabLayout-> ", "onTabSelected: $tab ")
                tab?.position.let { itPosition->
                   when(itPosition){
                       0->{ (binding.rcMostLikedPost.adapter as AllPostAdapter).submitList(list1) }
                       1->{(binding.rcMostLikedPost.adapter as AllPostAdapter).submitList(list2)}
                   }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("tabLayout-> ", "onTabUnSelected: $tab ")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("tabLayout-> ", "onTabReSelected: $tab ")
            }

        })
    }

}