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
import com.avalon.calizer.utils.AnalyzeAdapterType
import javax.inject.Inject

class AnalyzeFragment : Fragment() {
    private lateinit var binding: AnalyzeFragmentBinding
    private val viewsAdapter by lazy { AnalyzeViewAdapter() }
    private lateinit var viewModel: AnalyzeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AnalyzeFragmentBinding.inflate(inflater, container, false)
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

    }

    private fun setupRecyclerview() {
        binding.rcViewTitle.adapter = viewsAdapter
        binding.rcViewTitle.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }

    fun viewList(resources: Resources): ArrayList<AnalyzeViewData> {
        return arrayListOf(
            AnalyzeViewData(
                uri = R.drawable.ic_story_ico,
                title = resources.getString(R.string.analyze_title_stories),
                type = R.id.action_destination_analyze_to_storyFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_resolution,
                title = resources.getString(R.string.analyze_title_posts),
                type = AnalyzeAdapterType.ALL_POST.type
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_poz_ico,
                title = resources.getString(R.string.analyze_title_all_followers),
                type = R.id.action_destination_analyze_to_allFollowersFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_poz_ico,
                title = resources.getString(R.string.analyze_title_all_following),
                type = R.id.action_destination_analyze_to_allFollowingFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_poz_ico,
                title = resources.getString(R.string.analyze_title_new_followers),
                type = R.id.action_destination_analyze_to_newFollowersFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_neg_ico,
                title = "Takipten Çıkanlar",
                type = R.id.action_destination_analyze_to_unFollowersFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_neg_ico,
                title = "Takip Etmeyenler",
                type = R.id.action_destination_analyze_to_noFollowFragment
            )

        )
    }

}