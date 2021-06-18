package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.databinding.FollowViewItemBinding
import com.avalon.calizer.utils.clearRecycled
import com.avalon.calizer.utils.loadPPUrl
import com.bumptech.glide.Glide

class FollowsAdapter:RecyclerView.Adapter<FollowsAdapter.MainViewHolder>() {
    private var _followsList = emptyList<FollowData>()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
       val binding = FollowViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
       holder.bind(_followsList[position])
    }

    override fun getItemCount(): Int {
        return _followsList.size
    }

    fun setData(followList:List<FollowData>){
        _followsList = followList
        notifyDataSetChanged()
    }
}