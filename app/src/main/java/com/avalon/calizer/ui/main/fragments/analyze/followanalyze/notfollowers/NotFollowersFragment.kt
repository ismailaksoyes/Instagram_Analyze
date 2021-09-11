package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.notfollowers

import android.os.Bundle
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
import com.avalon.calizer.databinding.FragmentNoFollowBinding
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowViewModel
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.newfollowers.NewFollowersViewModel
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.followersToFollowList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotFollowersFragment : Fragment() {

    @Inject
    lateinit var viewModel:NotFollowersViewModel

    @Inject
    lateinit var prefs:MySharedPreferences

    private lateinit var binding: FragmentNoFollowBinding
    private val followsAdapter by lazy { FollowsAdapter() }
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        layoutManager = LinearLayoutManager(view.context)
        binding.rcNoFollowData.layoutManager = layoutManager
        observeFollowData()
        loadData(0)
        scrollListener()
        binding.ivBackBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_noFollowFragment_to_destination_analyze)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoFollowBinding.inflate(inflater,container,false)
       return binding.root
    }
    private fun setupRecyclerview() {
        binding.rcNoFollowData.adapter = followsAdapter
        binding.rcNoFollowData.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }
    fun loadData(startItem: Int) {
        lifecycleScope.launch {
            viewModel.updateNotFollowFlow()
            viewModel.getFollowData(startItem)
        }
    }
    fun updatePpItemReq(followData: List<FollowersData>) {
        lifecycleScope.launchWhenStarted {
            followData.forEach { data ->
                data.dsUserID?.let {
                    viewModel.getUserDetails(it)
                }

            }
        }

    }

    private fun observePpItemRes() {
        lifecycleScope.launchWhenStarted {
            viewModel.updateNotFollowers.collectLatest {
                when (it) {
                    is NotFollowersViewModel.UpdateState.Success -> {
                        it.userDetails.data?.user.let { userData ->
                            followsAdapter.updatePpItem(userData?.pk, userData?.profilePicUrl)
                        }
                    }

                }
            }

        }
    }

    private fun scrollListener() {
        binding.rcNoFollowData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        lifecycleScope.launchWhenStarted {
            viewModel.notFollowers.collectLatest {
                when (it) {
                    is NotFollowersViewModel.NotFollowersState.Success -> {
                        followsAdapter.removeLoadingView()
                        val followData = it.followData.followersToFollowList()
                        followsAdapter.setData(followData)
                        isLoading = false
                    }
                    is NotFollowersViewModel.NotFollowersState.Loading -> {
                        val data = FollowData(uid = -1)
                        followsAdapter.setLoading(data)
                    }
                    is NotFollowersViewModel.NotFollowersState.UpdateItem -> {
                        updatePpItemReq(it.followData)
                    }

                    else -> {
                    }
                }

            }
        }
    }

}