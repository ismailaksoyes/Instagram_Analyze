package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.newfollowers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.R
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.databinding.FragmentNewFollowersBinding
import com.avalon.calizer.ui.base.BaseFollowFragment
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.ui.custom.CustomToolbar
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.followersToFollowList
import com.avalon.calizer.utils.followingToFollowList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewFollowersFragment : BaseFollowFragment<FragmentNewFollowersBinding>(FragmentNewFollowersBinding::inflate) {
    val viewModel: NewFollowersViewModel  by viewModels()


    override fun initCreated() {

    }

    override fun getRecyclerView(): RecyclerView {
        return binding.rcFollowData
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

    override fun observePpItemRes(itemRes: (Pair<String, Long>) -> Unit) {
        lifecycleScope.launch {
            viewModel.profileUrl.collect {
                itemRes.invoke(it)
            }
        }
    }

    override fun observeFollowData(itemRes: (List<FollowData>) -> Unit) {
        lifecycleScope.launch {
            viewModel.allFollowers.collect {
                itemRes.invoke(it.followersToFollowList())
            }
        }
    }


}