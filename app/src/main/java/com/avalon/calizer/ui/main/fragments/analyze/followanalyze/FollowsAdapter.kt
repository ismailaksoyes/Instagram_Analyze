package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.databinding.FollowViewItemBinding
import com.avalon.calizer.utils.clearRecycled
import com.avalon.calizer.utils.loadPPUrl
import com.bumptech.glide.Glide

class FollowsAdapter:PagingDataAdapter<FollowData,RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object{
        private val REPO_COMPARATOR = object :DiffUtil.ItemCallback<FollowData>(){
            override fun areItemsTheSame(oldItem: FollowData, newItem: FollowData): Boolean =
              oldItem == newItem


            override fun areContentsTheSame(oldItem: FollowData, newItem: FollowData): Boolean =
                oldItem == newItem

        }
    }

    class MainViewHolder(var binding:FollowViewItemBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(followList:FollowData?){
            followList?.let { data->
                binding.ivViewPp.loadPPUrl(data.profilePicUrl)
                binding.tvPpUsername.text = "@${data.username}"
                if (!data.fullName.isNullOrEmpty()){
                    binding.tvPpFullname.text = data.fullName
                }else{
                    binding.tvPpFullname.visibility = View.GONE
                }
            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val binding = FollowViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MainViewHolder)?.bind(followList = getItem(position))
    }
}