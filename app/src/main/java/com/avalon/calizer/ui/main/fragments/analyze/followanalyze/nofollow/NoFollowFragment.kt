package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.nofollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentNoFollowBinding
import com.avalon.calizer.ui.main.fragments.analyze.followanalyze.FollowsAdapter


class NoFollowFragment : Fragment() {

    private lateinit var binding: FragmentNoFollowBinding
    private val followsAdapter by lazy { FollowsAdapter() }
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(view.context)
        binding.rcNoFollowData.layoutManager = layoutManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoFollowBinding.inflate(inflater,container,false)
       return binding.root
    }

}