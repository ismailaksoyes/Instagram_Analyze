package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.databinding.FollowItemLoadingBinding
import com.avalon.calizer.databinding.FollowViewItemBinding
import com.avalon.calizer.utils.getItemByID
import com.avalon.calizer.utils.loadPPUrl

class FollowsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var _followsList = arrayListOf<FollowData>()


    class MainViewHolder(var binding: FollowViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(followList: FollowData?) {
            followList?.let { data ->
                data.profilePicUrl?.let {
                    binding.ivViewPp.loadPPUrl(it)
                } ?: kotlin.run {
                    binding.ivViewPp.setImageDrawable(null)
                }


                getSubString(data.username)?.let { itUserName ->
                    binding.tvPpUsername.text = "@${itUserName}"
                }

                getSubString(data.fullName)?.let { itFullName ->
                    binding.tvPpFullname.text = itFullName
                } ?: kotlin.run { binding.tvPpFullname.visibility = View.GONE }

            }

        }

        private fun getSubString(name: String?): String? {
            return name?.let { itName ->
                when (itName.length) {
                    in 1..9 -> {
                        itName
                    }
                    in 10..50 -> {
                        "${itName.substring(0, 9)}..."
                    }
                    else -> {
                        null
                    }

                }
            }
        }
    }


    private fun getItemPosition(item: Long?): Int {
        return _followsList.getItemByID(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            FollowViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        _followsList[position].let { data ->
            (holder as MainViewHolder).bind(data)
        }

    }

    override fun getItemCount(): Int {
        return _followsList.size
    }


    fun setData(followList: List<FollowData>) {
        _followsList.addAll(followList)
        notifyDataSetChanged()
    }


    fun updatePpItem(item: Long?, profilePhoto: String?) {
        _followsList[getItemPosition(item)].profilePicUrl = profilePhoto
        notifyItemChanged(getItemPosition(item))

    }
}



