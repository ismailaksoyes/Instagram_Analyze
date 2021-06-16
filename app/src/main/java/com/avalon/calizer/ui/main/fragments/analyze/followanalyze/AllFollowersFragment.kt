package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()

        lifecycleScope.launchWhenStarted {
            viewModel.followers.collectLatest {
              followsAdapter.submitData(it)
            }
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


}