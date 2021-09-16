package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.R
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.databinding.FragmentAllFollowingBinding
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowers.AllFollowersViewModel
import com.avalon.calizer.utils.followersToFollowList
import com.avalon.calizer.utils.followingToFollowList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllFollowingFragment : Fragment() {

    @Inject
    lateinit var viewModel: AllFollowingViewModel

    lateinit var binding: FragmentAllFollowingBinding

    private val followsAdapter by lazy { FollowsAdapter() }
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

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
           it.findNavController().navigate(R.id.action_allFollowingFragment_to_destination_analyze)
        }
    }

    private fun setupRecyclerview() {
        binding.rcFollowData.adapter = followsAdapter
        binding.rcFollowData.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }

    private fun updatePpItemReq(followData: List<FollowingData>) {
        lifecycleScope.launch {
            followData.forEach { data ->
                data.dsUserID?.let {
                    viewModel.getUserDetails(it)
                }

            }
        }

    }

    private fun observePpItemRes() {
        lifecycleScope.launch {
            viewModel.updateAllFollowing.collect {
                when (it) {
                    is AllFollowingViewModel.UpdateState.Success -> {
                        it.userDetails.user.apply {
                            followsAdapter.updatePpItem(pk, profilePicUrl)
                        }
                    }

                }
            }

        }
    }
    fun loadData(startItem: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
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
            viewModel.allFollowing.collect {
                when (it) {
                    is AllFollowingViewModel.AllFollowingState.Success -> {
                            followsAdapter.setData(it.followData.followingToFollowList())
                            isLoading = false

                    }
                    is AllFollowingViewModel.AllFollowingState.UpdateItem -> {
                        updatePpItemReq(it.followData)
                    }

                    else -> {
                    }
                }

            }
        }
    }


}