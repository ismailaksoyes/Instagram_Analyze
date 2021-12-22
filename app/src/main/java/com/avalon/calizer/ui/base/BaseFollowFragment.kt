package com.avalon.calizer.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.shared.localization.LocalizationManager
import com.avalon.calizer.ui.custom.CustomToolbar
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter
import com.avalon.calizer.utils.followersToFollowList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import java.util.concurrent.Flow

abstract class BaseFollowFragment<viewBinding: ViewBinding>(private val inflate: Inflate<viewBinding>) :
    Fragment() {


    abstract fun getRecyclerView():RecyclerView
    abstract fun getCustomToolbar():CustomToolbar


    private lateinit var _binding:viewBinding
    protected val binding get() = _binding
    private lateinit var recyclerView: RecyclerView
    private lateinit var customToolbar: CustomToolbar
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater,container,false)
        createView()
        init()
       // loadData(0)
        collectItem()
        initCreated()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    private fun init(){
        setupRecyclerview()
        toolbarSettings()
    }

    abstract fun initCreated()

    private fun createView(){
        recyclerView = getRecyclerView()
        customToolbar = getCustomToolbar()
        layoutManager = getLayoutManager()
    }


    private fun setupRecyclerview() {
        recyclerView.adapter = FollowsAdapter { itFollowData ->
            updatePpItemReq(itFollowData)
        }
        recyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerView.setHasFixedSize(true)

    }

    abstract fun updatePpItemReq(followData: FollowData)


    abstract fun getLayoutManager():LinearLayoutManager


    abstract  fun observePpItemRes(itemRes:(Pair<String,Long>)->Unit)

    abstract  fun observeFollowData(itemRes:(List<FollowData>)->Unit)


    private fun collectItem(){
        observeFollowData {
            (recyclerView.adapter as FollowsAdapter).addItem(it)
            isLoading = false
        }
        observePpItemRes {
            (recyclerView.adapter as FollowsAdapter).updateProfileImage(
                it.first,
                it.second
            )
        }
    }



    private fun toolbarSettings(){
        customToolbar.onBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }





}