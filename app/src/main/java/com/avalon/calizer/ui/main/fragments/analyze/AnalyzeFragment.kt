package com.avalon.calizer.ui.main.fragments.analyze

import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalon.calizer.R
import com.avalon.calizer.data.local.analyze.AnalyzeViewData
import com.avalon.calizer.databinding.AnalyzeFragmentBinding
import com.avalon.calizer.shared.localization.LocalizationManager
import com.avalon.calizer.shared.model.LocalizationType
import com.avalon.calizer.ui.accounts.adapters.AccountsAdapter
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.utils.AnalyzeAdapterType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnalyzeFragment : BaseFragment<AnalyzeFragmentBinding>(AnalyzeFragmentBinding::inflate) {

    private val viewsAdapter by lazy { AnalyzeViewAdapter() }
    val viewModel: AnalyzeViewModel by viewModels()

    @Inject
    lateinit var localizationManager: LocalizationManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.localization = localizationManager
        setupRecyclerview()
        viewsAdapter.setData(createViewList())
    }



    private fun setupRecyclerview() {
        binding.rcViewTitle.adapter = viewsAdapter
        binding.rcViewTitle.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL, false
        )

    }

    private fun getTextLocalization(key:String):String{
        return localizationManager.localization(key)
    }

    private fun createViewList(): ArrayList<AnalyzeViewData> {
        return arrayListOf(
            AnalyzeViewData(
                uri = R.drawable.ic_story_ico,
                title = getTextLocalization(LocalizationType.ANALYZE_STORYANALYZE_TITLE),
                type = R.id.action_destination_analyze_to_storyFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_resolution,
                title = getTextLocalization(LocalizationType.ANALYZE_ALLPOSTS_TITLE),
                type = AnalyzeAdapterType.ALL_POST.type
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_poz_ico,
                title =  getTextLocalization(LocalizationType.ANALYZE_ALLFOLLOWERS_TITLE),
                type = R.id.action_destination_analyze_to_allFollowersFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_poz_ico,
                title =  getTextLocalization(LocalizationType.ANALYZE_ALLFOLLOWING_TITLE),
                type = R.id.action_destination_analyze_to_allFollowingFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_poz_ico,
                title =  getTextLocalization(LocalizationType.ANALYZE_NEWFOLLOWERS_TITLE),
                type = R.id.action_destination_analyze_to_newFollowersFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_neg_ico,
                title =  getTextLocalization(LocalizationType.ANALYZE_UNFOLLOWERS_TITLE),
                type = R.id.action_destination_analyze_to_unFollowersFragment
            ),
            AnalyzeViewData(
                uri = R.drawable.ic_follow_neg_ico,
                title =  getTextLocalization(LocalizationType.ANALYZE_NOTFOLLOWERS_TITLE),
                type = R.id.action_destination_analyze_to_noFollowFragment
            )

        )
    }

}