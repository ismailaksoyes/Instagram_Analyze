package com.avalon.calizer.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface IBaseFollow: ViewBinding  {

    fun getFollowerListView(): RecyclerView
}