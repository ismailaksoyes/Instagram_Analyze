package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.R
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.databinding.FragmentAllFollowersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllFollowersFragment : Fragment() {
    private lateinit var binding: FragmentAllFollowersBinding

    @Inject
    lateinit var viewModel: FollowViewModel
    private val followsAdapter by lazy { FollowsAdapter() }


    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        lifecycleScope.launch {
            viewModel.updateFlow()
            viewModel.getFollowData(0)
        }
        binding.rcFollowData.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recyclerView?.let {
                    Log.d("Recyc"," childcount-> ${it.layoutManager?.childCount}")
                    Log.d("Recyc"," itemcount-> ${it.layoutManager?.itemCount}")
                    Log.d("Recyc","${it.layoutManager?.findViewByPosition(0)}")
                }

            }

        })


        lifecycleScope.launchWhenStarted {
            viewModel.allFollow.collectLatest {
                binding.pbFollowData.isVisible = it is FollowViewModel.FollowState.Loading
                when(it){

                    is FollowViewModel.FollowState.Success->{
                        followsAdapter.setData(it.followData)
                    }else->{}
                }

            }
        }

        /**
        lifecycleScope.launch {
            followsAdapter.loadStateFlow.collectLatest {loadStates ->
                binding.pbFollowData.isVisible = loadStates.refresh is LoadState.Loading
            }
        }
        **/
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllFollowersBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun setupRecyclerview() {
        binding.rcFollowData.adapter = followsAdapter
        binding.rcFollowData.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }


}