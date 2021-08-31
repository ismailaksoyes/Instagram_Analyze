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
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.databinding.FragmentNewFollowersBinding
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowViewModel
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter
import com.avalon.calizer.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewFollowersFragment : Fragment() {
    @Inject
    lateinit var viewModel: FollowViewModel
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
            viewModel.updateNoFollowFlow()
            viewModel.getUnFollowers(prefs.selectedAccount,startItem)
        }
    }
    private fun updatePpItemReq(followData: List<FollowData>) {
        lifecycleScope.launchWhenStarted {
            followData.forEach { data ->
                data.dsUserID?.let {
                    viewModel.getUserDetails(it)
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
            viewModel.unFollowersData.collectLatest {
                when (it) {
                    is FollowViewModel.UnFollowersState.Success -> {
                        followsAdapter.removeLoadingView()
                        followsAdapter.setData(it.followData)
                        isLoading = false
                    }
                    is FollowViewModel.UnFollowersState.Loading -> {
                        val data = FollowData(type = 5)
                        followsAdapter.setLoading(data)
                    }
                    is FollowViewModel.UnFollowersState.UpdateItem -> {
                        updatePpItemReq(it.followData)
                    }

                    else -> {
                    }
                }

            }
        }
    }


}