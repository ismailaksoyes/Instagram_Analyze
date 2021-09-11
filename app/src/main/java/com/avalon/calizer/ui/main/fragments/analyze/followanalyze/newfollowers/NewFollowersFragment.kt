package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.newfollowers

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
import com.avalon.calizer.databinding.FragmentNewFollowersBinding
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowViewModel
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allfollowers.AllFollowersViewModel
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.followersToFollowList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewFollowersFragment : Fragment() {
    @Inject
    lateinit var viewModel: NewFollowersViewModel
    @Inject
    lateinit var prefs: MySharedPreferences

    private val followsAdapter by lazy { FollowsAdapter() }
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading: Boolean = false

    private lateinit var binding: FragmentNewFollowersBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewFollowersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        layoutManager = LinearLayoutManager(view.context)
        binding.rcNewFollowersData.layoutManager = layoutManager
        observeFollowData()
        observePpItemRes()
        loadData(0)
        scrollListener()
        binding.ivBackBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_newFollowersFragment_to_destination_analyze)
        }
    }

    private fun setupRecyclerview() {
        binding.rcNewFollowersData.adapter = followsAdapter
        binding.rcNewFollowersData.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }
    fun loadData(startItem: Int) {
        lifecycleScope.launch {
            viewModel.updateNewFollowFlow()
            viewModel.getFollowData(startItem)
        }
    }
    private fun updatePpItemReq(followData: List<FollowersData>) {
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
            viewModel.updateNewFollowers.collectLatest {
                when (it) {
                    is NewFollowersViewModel.UpdateState.Success -> {
                        it.userDetails.data?.user.let { userData ->
                            followsAdapter.updatePpItem(userData?.pk, userData?.profilePicUrl)
                        }
                    }

                }
            }

        }
    }
    private fun scrollListener() {
        binding.rcNewFollowersData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            viewModel.newFollowers.collectLatest {
                when (it) {
                    is NewFollowersViewModel.NewFollowersState.Success -> {
                        followsAdapter.removeLoadingView()
                        val followData = it.followData.followersToFollowList()
                        followsAdapter.setData(followData)
                        isLoading = false
                    }
                    is NewFollowersViewModel.NewFollowersState.Loading -> {
                        val data = FollowData(uid = -1)
                        followsAdapter.setLoading(data)
                    }
                    is NewFollowersViewModel.NewFollowersState.UpdateItem -> {
                        updatePpItemReq(it.followData)
                    }

                    else -> {
                    }
                }

            }
        }
    }


}