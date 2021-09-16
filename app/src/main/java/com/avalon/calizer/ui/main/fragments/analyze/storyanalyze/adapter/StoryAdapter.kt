package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.databinding.StoryViewsItemBinding
import com.avalon.calizer.utils.loadPPUrl

class StoryAdapter(val showStoryInterface: ShowStoryInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var storyList = emptyList<StoryData>()

    class MainViewHolder(val binding: StoryViewsItemBinding,val showStoryInterface: ShowStoryInterface):RecyclerView.ViewHolder(binding.root){
        fun bind(storyData: StoryData){
            binding.ivStoryView.loadPPUrl(storyData.imageUrl)
            binding.ivStoryView.setOnClickListener {
                showStoryInterface.openStory(storyData.pk)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = StoryViewsItemBinding.inflate(LayoutInflater.from(parent.context))
       return MainViewHolder(binding,showStoryInterface)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MainViewHolder).bind(storyList[position])
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    fun setStoryData(storyData: List<StoryData>){
        storyList = storyData
        notifyDataSetChanged()
    }

}