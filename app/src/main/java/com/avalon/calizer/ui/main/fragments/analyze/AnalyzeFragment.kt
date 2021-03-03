package com.avalon.calizer.ui.main.fragments.analyze

import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.analyze.AnalyzeViewData
import com.avalon.calizer.databinding.AnalyzeFragmentBinding
import com.avalon.calizer.ui.accounts.adapters.AccountsAdapter

class AnalyzeFragment : Fragment() {
    private lateinit var binding: AnalyzeFragmentBinding
    private val viewsAdapter by lazy { AnalyzeViewAdapter() }
    private lateinit var viewModel: AnalyzeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AnalyzeFragmentBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resources = view.resources
        setupRecyclerview()
        viewsAdapter.setData(viewList(resources))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AnalyzeViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun setupRecyclerview() {
        binding.rcViewTitle.adapter = viewsAdapter
        binding.rcViewTitle.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.VERTICAL,false)

    }
    fun viewList(resources: Resources):ArrayList<AnalyzeViewData>{
        return arrayListOf(
            AnalyzeViewData(
                uri = R.drawable.common_full_open_on_phone,
                title = resources.getString(R.string.analyze_title_stories)
            ),
            AnalyzeViewData(
                uri = R.drawable.common_full_open_on_phone,
                title = resources.getString(R.string.analyze_title_stories)
            ),
            AnalyzeViewData(
                uri = R.drawable.common_full_open_on_phone,
                title = resources.getString(R.string.analyze_title_stories)
            ),
            AnalyzeViewData(
                uri = R.drawable.common_full_open_on_phone,
                title = resources.getString(R.string.analyze_title_stories)
            )


        )
    }

}