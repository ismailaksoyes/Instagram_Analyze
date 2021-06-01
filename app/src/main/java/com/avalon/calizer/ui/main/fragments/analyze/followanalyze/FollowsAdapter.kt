package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.databinding.FollowViewItemBinding
import com.avalon.calizer.utils.loadPPUrl

class FollowsAdapter:RecyclerView.Adapter<FollowsAdapter.MainViewHolder>() {
    private var _accountsList = emptyList<FollowData>()

    class MainViewHolder(var binding:FollowViewItemBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(followList:FollowData){
            followList.let {
                binding.ivViewPp.loadPPUrl(followList.profilePicUrl)
                binding.tvPpFullname.text = followList.fullName
                binding.tvPpUsername.text = "@$followList.username"
            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
       val binding = FollowViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
       holder.bind(_accountsList[position])
    }

    override fun getItemCount(): Int {
        return _accountsList.size
    }
    fun setData(followList: List<FollowData>){
        _accountsList = followList
        notifyDataSetChanged()
    }
}