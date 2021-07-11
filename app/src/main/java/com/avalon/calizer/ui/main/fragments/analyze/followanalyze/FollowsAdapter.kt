package com.avalon.calizer.ui.main.fragments.analyze.followanalyze

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.FollowData
import com.avalon.calizer.databinding.FollowItemLoadingBinding
import com.avalon.calizer.databinding.FollowViewItemBinding
import com.avalon.calizer.utils.clearRecycled
import com.avalon.calizer.utils.getItemByID
import com.avalon.calizer.utils.loadPPUrl
import com.bumptech.glide.Glide

class FollowsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LOADING = 1
    }

    private var _followsList = arrayListOf<FollowData>()

    class LoadingViewHolder(var binding: FollowItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(followList: FollowData) {

        }

    }

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
                binding.tvPpUsername.text = "@${data.username}"
                if (!data.fullName.isNullOrEmpty()) {
                    binding.tvPpFullname.text = data.fullName
                } else {
                    binding.tvPpFullname.visibility = View.GONE
                }
            }

        }
    }

    fun updatePpItem(item: Long?, profilePhoto: String?) {
        _followsList[getItemPosition(item)].profilePicUrl = profilePhoto
        notifyItemChanged(getItemPosition(item))

    }

    fun getItemPosition(item: Long?): Int {
        return _followsList.getItemByID(item)
    }


    override fun getItemViewType(position: Int): Int {
        _followsList[position].let {
            return if (it.type?.toInt() == 5) 1 else 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("ViewType", viewType.toString())

        return if (viewType == VIEW_TYPE_LOADING) {
            val binding =
                FollowItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        } else {

            val binding =
                FollowViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MainViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.d("GetPosition", getItemPosition(_followsList[position].dsUserID).toString())
        _followsList[position].let { data ->
            if (data.type?.toInt() == 5) {
                (holder as LoadingViewHolder).bind(_followsList[position])
            } else {
                (holder as MainViewHolder).bind(_followsList[position])
            }

        }

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

    fun setLoading(followList: FollowData) {
        _followsList.add(followList)
        notifyDataSetChanged()
    }
}



