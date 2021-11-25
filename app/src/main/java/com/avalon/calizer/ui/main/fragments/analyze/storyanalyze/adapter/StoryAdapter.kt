package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.story.StoryData
import com.avalon.calizer.databinding.StoryViewsItemBinding
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.StoryViewModel
import com.avalon.calizer.utils.loadPPUrl
import javax.inject.Inject

class StoryAdapter(private val storyItemCLick:(Long)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var storyList = emptyList<StoryData>()


    class MainViewHolder(val binding: StoryViewsItemBinding,private val storyItemCLick:(Long)->Unit):RecyclerView.ViewHolder(binding.root){
        fun bind(storyData: StoryData){
            binding.ivStoryView.loadPPUrl(storyData.imageUrl)
            binding.ivStoryView.setOnClickListener {
                storyData.pk?.let { itPk->
                    storyItemCLick.invoke(itPk)
                }

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = StoryViewsItemBinding.inflate(LayoutInflater.from(parent.context))
       return MainViewHolder(binding,storyItemCLick)
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