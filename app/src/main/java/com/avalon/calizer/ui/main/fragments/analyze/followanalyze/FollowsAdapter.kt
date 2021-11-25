package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.ObjectsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.databinding.FollowItemLoadingBinding
import com.avalon.calizer.databinding.FollowViewItemBinding
import com.avalon.calizer.utils.getItemByID
import com.avalon.calizer.utils.loadPPUrl
import com.avalon.calizer.utils.toShortName

class FollowsAdapter : ListAdapter<FollowData, FollowViewHolder>(DiffCallback()) {

    private var followList = mutableListOf<FollowData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
      val binding = FollowViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FollowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    fun setData(list: List<FollowData>){
        followList = list.toMutableList()
        submitList(followList)
    }

}

private class DiffCallback : DiffUtil.ItemCallback<FollowData>() {
    override fun areItemsTheSame(oldItem: FollowData, newItem: FollowData): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: FollowData, newItem: FollowData): Boolean {
       // return ObjectsCompat.equals(oldItem, newItem)
        return oldItem == newItem
    }

}



class FollowViewHolder(private val binding: FollowViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(followData: FollowData?) {
        followData?.let { data ->
            data.profilePicUrl?.let {
                binding.ivViewPp.loadPPUrl(it)
            } ?: kotlin.run {
                binding.ivViewPp.setImageDrawable(null)
            }
            data.username?.let { itUsername->
                binding.tvPpUsername.text = itUsername.toShortName()
            }
            data.fullName?.let { itFullName->
                if (itFullName.isNotEmpty()){
                    binding.tvPpFullname.text = itFullName.toShortName()
                }else{
                    binding.tvPpFullname.visibility= View.GONE
                }

            }?: kotlin.run { binding.tvPpFullname.visibility= View.GONE}
        }
    }
}




