package com.avalon.calizer.ui.tutorial

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.R
import com.avalon.calizer.data.local.TutorialData
import com.avalon.calizer.databinding.FragmentTutorialBinding

class TutorialPagerAdapter():
    RecyclerView.Adapter<TutorialPagerAdapter.TutorialViewHolder>() {
    var dataList = ArrayList<TutorialData>()
    class TutorialViewHolder(var binding: FragmentTutorialBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(dataList: TutorialData){
           binding.tvIntroDesc.text = dataList.descText
            binding.ivIntroView.setImageResource(dataList.drawable)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder {
        val binding = FragmentTutorialBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        setData()
        return TutorialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        holder.bind(dataList[position])

    }

    override fun getItemCount(): Int {
        return 3
    }
    fun setData(){
        dataList.add(TutorialData(R.drawable.common_google_signin_btn_icon_dark,"Test1"))
        dataList.add(TutorialData(R.drawable.common_google_signin_btn_icon_dark,"Test2"))
        dataList.add(TutorialData(R.drawable.common_google_signin_btn_icon_dark,"Test3"))
        //notifyDataSetChanged()

    }

}