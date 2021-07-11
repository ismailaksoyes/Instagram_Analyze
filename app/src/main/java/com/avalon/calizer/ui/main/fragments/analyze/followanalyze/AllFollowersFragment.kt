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
import androidx.navigation.findNavController
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
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading: Boolean = false
    fun loadData(startItem:Int){
        lifecycleScope.launch {
            viewModel.updateFlow()
            viewModel.getFollowData(startItem)
        }
    }
    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        layoutManager = LinearLayoutManager(view.context)
        binding.rcFollowData.layoutManager =  layoutManager
        updatePpItemRes()
        initData()
        loadData(0)
        scrollListener()
        binding.ivBackBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_allFollowersFragment_to_destination_analyze)
        }

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
    fun updatePpItemReq(followData:List <FollowData>){
        lifecycleScope.launchWhenStarted {
            followData.forEach { data->
                data.dsUserID?.let {
                    Log.d("DsUserId",it.toString())
                    viewModel.getUserDetails(it)
                }

            }
        }

    }
    fun updatePpItemRes(){
        lifecycleScope.launchWhenStarted {
            viewModel.updateFollow.collectLatest {
                when(it){
                    is FollowViewModel.UpdateState.Success->{
                        it.userDetails.data?.user.let { userData->
                            followsAdapter.updatePpItem(userData?.pk,userData?.profilePicUrl)
                        }
                    }

                }
            }

        }
    }
    fun scrollListener(){

        binding.rcFollowData.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.layoutManager != null && recyclerView.layoutManager!!.itemCount>1){
                    if (!isLoading && recyclerView.layoutManager?.itemCount ==(layoutManager.findLastVisibleItemPosition()+1)){
                        Log.d("DataLoad",recyclerView.layoutManager!!.itemCount.toString())
                        loadData(recyclerView.layoutManager!!.itemCount)
                        isLoading = true
                    }
                }


            }

        })
    }
    fun initData(){
        lifecycleScope.launchWhenStarted {
            viewModel.allFollow.collectLatest {
                when(it){
                    is FollowViewModel.FollowState.Success->{
                        followsAdapter.removeLoadingView()
                        followsAdapter.setData(it.followData)
                        isLoading = false
                    }
                    is FollowViewModel.FollowState.Loading->{
                        val data = FollowData(type = 5)
                        followsAdapter.setLoading(data)
                    }
                    is FollowViewModel.FollowState.UpdateItem->{
                        updatePpItemReq(it.followData)
                    }

                    else->{}
                }

            }
        }
    }


}