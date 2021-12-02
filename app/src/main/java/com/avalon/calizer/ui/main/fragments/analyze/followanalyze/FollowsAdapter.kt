package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
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
import com.avalon.calizer.utils.glideCacheControl
import com.avalon.calizer.utils.loadPPUrl
import com.avalon.calizer.utils.toShortName
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class FollowsAdapter(private val followImageLoad:(FollowData)->Unit) : ListAdapter<FollowData, FollowViewHolder>(DiffCallback()) {

    private var followList = mutableListOf<FollowData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding =
            FollowViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding,followImageLoad)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    fun addItem(list: List<FollowData>) {
        val oldList = currentList.toMutableList()
        oldList.addAll(list)
        submitList(oldList)
    }

    fun updateItem(followData: FollowData){
        val newList = arrayListOf(followData)
        val oldList = currentList.toMutableList()
        oldList.addAll(newList)
        submitList(oldList)
    }

    fun updateProfileImage(url:String,userId:Long){
        if (url.isNotEmpty()){
            val list = currentList.toMutableList()

            list.withIndex().firstOrNull { it.value.dsUserID == userId }?.let { itItem->
             val index =  itItem.index
                val newItem = FollowData(
                    uid = itItem.value.uid,
                    analyzeUserId = itItem.value.analyzeUserId,
                    profilePicUrl = url,
                    dsUserID = itItem.value.dsUserID,
                    fullName = itItem.value.fullName,
                    username = itItem.value.username
                )
                list.removeAt(index)
                list.add(index,newItem)
                submitList(list)
            }

        }
    }

    fun removeItem(uid:Long?){
        uid?.let { itUid->
            val oldList = currentList.toMutableList()
            oldList.filter { it.uid ==itUid }.let { itItemList->
                if (itItemList.isNotEmpty()){
                    itItemList.forEach { itItem->
                        oldList.remove(itItem)
                        submitList(oldList)
                    }

                }
            }
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<FollowData>() {
    override fun areItemsTheSame(oldItem: FollowData, newItem: FollowData): Boolean {
        return oldItem.dsUserID == newItem.dsUserID
    }

    override fun areContentsTheSame(oldItem: FollowData, newItem: FollowData): Boolean {
        return oldItem.profilePicUrl.equals(newItem.profilePicUrl)
    }

}


class FollowViewHolder(private val binding: FollowViewItemBinding,private val followImageLoad:(FollowData)->Unit ) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(followData: FollowData?) {
        followData?.let { data ->
            data.profilePicUrl?.let {
                binding.ivViewPp.glideCacheControl(it){ isLoadImage->
                    if (!isLoadImage){
                        Log.d("isChangedImage", "No cache Item ${data.username} ")
                        followImageLoad.invoke(data)
                    }else{
                        Log.d("isChangedImage", "OK! cache Item ${data.username} ")
                    }
                }
            } ?: kotlin.run {
               // binding.ivViewPp.setImageDrawable(null)
            }
            data.username?.let { itUsername ->
                binding.tvPpUsername.text = itUsername.toShortName()
            }
            data.fullName?.let { itFullName ->
                if (itFullName.isNotEmpty()) {
                    binding.tvPpFullname.text = itFullName.toShortName()
                } else {
                    binding.tvPpFullname.visibility = View.GONE
                }

            } ?: kotlin.run { binding.tvPpFullname.visibility = View.GONE }
        }
    }
}




