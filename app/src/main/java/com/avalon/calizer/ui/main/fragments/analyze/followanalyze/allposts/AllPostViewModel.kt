package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allposts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPostViewModel @Inject constructor(private val repository: Repository,private val prefs:MySharedPreferences) :ViewModel() {

init {
    getAllMedia()
}

    private fun getAllMedia(){
        viewModelScope.launch {
            val sb = StringBuilder()
            sb.append("https://instagram.com/").append(prefs.userName).append("1/?__a=1")
            val url = sb.toString()
            when(val response = repository.getUserAllMedia(url,prefs.allCookie)){
                is Resource.Success->{
                    val data = response.data?.graphql?.user?.edgeOwnerToTimelineMedia?.edges
                    data?.forEach { itData->
                        Log.d("responseAllMedia-> ", "getAllMedia: ${itData.node}")
                    }
                }
            }
        }
    }



}