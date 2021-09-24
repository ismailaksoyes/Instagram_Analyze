package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.story.HighlightsData
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.databinding.StoryViewsItemBinding
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.StoryViewModel
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.dialog.HighlightsSheetViewModel
import com.avalon.calizer.utils.loadPPUrl

class HighlightsAdapter(val viewModel: HighlightsSheetViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var highlightsList = emptyList<HighlightsData>()


    class MainViewHolder(val binding: StoryViewsItemBinding, val viewModel: HighlightsSheetViewModel):
        RecyclerView.ViewHolder(binding.root){
        fun bind(storyData: HighlightsData){
            binding.ivStoryView.loadPPUrl(storyData.imageUrl)
            binding.ivStoryView.setOnClickListener {
                viewModel.setClickItemId(storyData.highlightsId)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = StoryViewsItemBinding.inflate(LayoutInflater.from(parent.context))
        return MainViewHolder(binding,viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MainViewHolder).bind(highlightsList[position])
    }

    override fun getItemCount(): Int {
        return highlightsList.size
    }

    fun setHighlightsData(highlightsData: List<HighlightsData>){
        highlightsList = highlightsData
        notifyDataSetChanged()
    }

}