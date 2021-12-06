package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowers

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.avalon.calizer.ui.base.BaseFollowFragment
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.ui.custom.CustomToolbar
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter
import com.avalon.calizer.utils.followersToFollowList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@AndroidEntryPoint
class AllFollowersFragment :
    BaseFollowFragment<FragmentAllFollowersBinding>(FragmentAllFollowersBinding::inflate) {

    val viewModel: AllFollowersViewModel by viewModels()

    private var isLoading: Boolean = false

    override fun getRecyclerView(): RecyclerView {
        return binding.rcFollowData
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observeFollowData()
        //observePpItemRes()
        //loadData(0)
        //scrollListener()
        //init()

    }

    override fun getToolbarTitle(): String {
        return "testTitle"
    }

    override fun getCustomToolbar(): CustomToolbar {
        return binding.toolbar
    }


    override fun updatePpItemReq(followData: FollowData) {
        lifecycleScope.launch {
            followData.dsUserID?.let { itDsUserId ->
                viewModel.getUserDetails(itDsUserId)
            }
        }
    }

    override fun getLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    override fun loadData(itemSize: Int) {
        lifecycleScope.launch {
            viewModel.getFollowData(itemSize)
        }
    }

    override suspend fun observePpItemRes() {
        lifecycleScope.launch {
            viewModel.profileUrl.collect {
                return@collect
            }
        }
    }

    override suspend fun observeFollowData(): MutableSharedFlow<List<FollowersData>> {
        lifecycleScope.launch {
            viewModel.allFollowers.collect {
                return@collect
            }
        }
    }

    private fun observePpItemRes1() {
        lifecycleScope.launch {
            viewModel.profileUrl.collect { itItem ->
                (binding.rcFollowData.adapter as FollowsAdapter).updateProfileImage(
                    itItem.first,
                    itItem.second
                )
            }

        }
    }


    private fun observeFollowData1() {
        lifecycleScope.launch {
            viewModel.allFollowers.collect {
                (binding.rcFollowData.adapter as FollowsAdapter).addItem(it.followersToFollowList())
                isLoading = false
            }
        }
    }


}