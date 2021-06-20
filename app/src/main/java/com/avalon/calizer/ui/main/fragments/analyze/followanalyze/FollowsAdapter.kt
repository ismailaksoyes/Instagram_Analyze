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
import com.avalon.calizer.databinding.FollowItemLoadingBinding
import com.avalon.calizer.databinding.FollowViewItemBinding
import com.avalon.calizer.utils.clearRecycled
import com.avalon.calizer.utils.loadPPUrl
import com.bumptech.glide.Glide

class FollowsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LOADING = 1
        const val VIEW_TYPE_ITEM = 0
    }

    private var _followsList = arrayListOf<FollowData>()

    class LoadingViewHolder(var binding: FollowItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class MainViewHolder(var binding: FollowViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(followList: FollowData?) {
            followList?.let { data ->
                binding.ivViewPp.loadPPUrl(data.profilePicUrl)
                binding.tvPpUsername.text = "@${data.username}"
                if (!data.fullName.isNullOrEmpty()) {
                    binding.tvPpFullname.text = data.fullName
                } else {
                    binding.tvPpFullname.visibility = View.GONE
                }
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            val binding =
                FollowViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MainViewHolder(binding)
        } else {
            val binding =
                FollowItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.bind(_followsList[position])
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            holder.it
        }

        override fun getItemCount(): Int {
            return _followsList.size
        }

        fun removeLoadingView() {
            if (_followsList.size != 0) {
                _followsList.removeAt(_followsList.size - 1)
                notifyItemRemoved(_followsList.size)
            }
        }

        fun setData(followList: List<FollowData>) {
            _followsList.addAll(followList)
            notifyDataSetChanged()
        }


    }
}



