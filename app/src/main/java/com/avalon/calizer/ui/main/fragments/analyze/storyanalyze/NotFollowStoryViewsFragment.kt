package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentNotFollowStoryViewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotFollowStoryViewsFragment : Fragment() {
    lateinit var binding: FragmentNotFollowStoryViewsBinding

    @Inject
    lateinit var viewModel: NotFollowStoryViewsViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotFollowStoryViewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    fun initData(){
        viewModel.setLoadingState()
        getFollowers()
    }

    fun getFollowers(){
        lifecycleScope.launch {
            viewModel.getFollowers()
        }
    }
    fun observeFollowers(){

    }

}