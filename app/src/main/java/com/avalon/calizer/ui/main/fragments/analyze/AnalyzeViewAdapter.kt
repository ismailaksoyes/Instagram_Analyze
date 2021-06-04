package com.avalon.calizer.ui.main.fragments.analyze

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.R
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.analyze.AnalyzeViewData
import com.avalon.calizer.databinding.AnalyzeViewItemBinding


class AnalyzeViewAdapter() : RecyclerView.Adapter<AnalyzeViewAdapter.MainViewHolder>() {
    private var _viewTitleList = ArrayList<AnalyzeViewData>()

    class MainViewHolder(var binding: AnalyzeViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceType")
        fun bind(viewList: AnalyzeViewData) {
            binding.tvAnalyzeTitle.text = viewList.title
            binding.ivViewIcon.setImageResource(viewList.uri)
            binding.cvAccounts.setOnClickListener {
                    it.findNavController().navigate(viewList.type)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            AnalyzeViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(_viewTitleList[position])
    }


    override fun getItemCount(): Int {
        return _viewTitleList.size
    }

    fun setData(accountsList: ArrayList<AnalyzeViewData>) {
        _viewTitleList = accountsList
        notifyDataSetChanged()

    }
}