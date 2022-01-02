package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allposts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.avalon.calizer.utils.AllPostState
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AllPostFragment : BaseFragment<FragmentAllPostBinding>(FragmentAllPostBinding::inflate) {


    private val viewModel:AllPostViewModel by viewModels()

    @Inject
    lateinit var localizationManager: LocalizationManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerview()
        observeTabLayout()
        observeAllPostData(savedInstanceState)
        binding.toolbar.setTitle = localizationManager.localization(LocalizationType.ANALYZE_ALLPOSTS_TITLE)

    }

    private fun observeAllPostData(savedInstanceState: Bundle?){
        lifecycleScope.launch {
            viewModel.allPostData.collectLatest {

                when(it){
                    is AllPostViewModel.PostState.Success->{
                        (binding.rcMostLikedPost.adapter as AllPostAdapter).setAllPost(it.data)
                        shimmerControl(false)
                        if (savedInstanceState!=null){
                            val tabPosition = savedInstanceState.getInt("tabState")
                            binding.pagerAnalyze.getTabAt(tabPosition)?.select()
                            setTabLayoutPosition(tabPosition)
                        }else{
                            setTabLayoutPosition(0)
                        }
                    }
                    is AllPostViewModel.PostState.Loading->{
                        shimmerControl(true)
                    }



                }


            }
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

    private fun changeAdapterState(postState: AllPostState){
        (binding.rcMostLikedPost.adapter as AllPostAdapter).setPostState(postState)
    }

    private fun setTabLayoutPosition(position:Int){
        when(position){
            0->{ changeAdapterState(AllPostState.MOST_LIKE) }
            1->{ changeAdapterState(AllPostState.MOST_COMMENT)}
        }
    }

    private fun shimmerControl(status:Boolean){
        if (status){
            binding.allPostView.visibility = View.GONE
            binding.postShimmerView.visibility = View.VISIBLE
        }else{
            binding.allPostView.visibility = View.VISIBLE
            binding.postShimmerView.visibility = View.GONE
        }
    }

    private fun observeTabLayout(){
        binding.pagerAnalyze.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { itPosition->
                  setTabLayoutPosition(itPosition)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

}