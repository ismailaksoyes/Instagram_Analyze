package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowers

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.R
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.databinding.FragmentAllFollowersBinding
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter
import com.avalon.calizer.utils.followersToFollowList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllFollowersFragment : BaseFragment<FragmentAllFollowersBinding>(FragmentAllFollowersBinding::inflate) {

    val viewModel: AllFollowersViewModel by viewModels()
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        layoutManager = LinearLayoutManager(view.context)
        binding.rcFollowData.layoutManager = layoutManager
        observeFollowData()
        observePpItemRes()
        loadData(0)
        scrollListener()
        binding.ivBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }



    }


    private fun setupRecyclerview() {
        binding.rcFollowData.adapter = FollowsAdapter{ itFollowData->
            updatePpItemReq(itFollowData)
        }
        binding.rcFollowData.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }

    private fun updatePpItemReq(followData: FollowData) {
        lifecycleScope.launch {
                followData.dsUserID?.let { itDsUserId->
                    viewModel.getUserDetails(itDsUserId)
                }
        }

    }

    private fun observePpItemRes() {
        lifecycleScope.launch {
            viewModel.profileUrl.collect { itItem->
                (binding.rcFollowData.adapter as FollowsAdapter).updateProfileImage(itItem.first,itItem.second)
            }

        }
    }
    fun loadData(startItem: Int) {
        lifecycleScope.launch {
            viewModel.getFollowData(startItem)
        }
    }
    private fun scrollListener() {
        binding.rcFollowData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recyclerView.layoutManager?.let { itLayoutManager ->
                    if (!isLoading && itLayoutManager.itemCount == (layoutManager.findLastVisibleItemPosition() + 1) && itLayoutManager.itemCount > 1) {
                        loadData(itLayoutManager.itemCount)
                        isLoading = true
                    }

                }
            }

        })
    }

    private fun observeFollowData(){
        lifecycleScope.launch {
            viewModel.allFollowers.collect {
                when (it) {
                    is AllFollowersViewModel.AllFollowersState.Success -> {
                        (binding.rcFollowData.adapter as FollowsAdapter).addItem(it.followData.followersToFollowList())
                            isLoading = false
                    }

                    else -> {
                    }
                }

            }
        }
    }



}