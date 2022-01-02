package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allposts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.analyze.PostViewData
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPostViewModel @Inject constructor(
    private val repository: Repository,
    private val prefs: MySharedPreferences
) : ViewModel() {


    val allPostData = MutableStateFlow<PostState>(PostState.Empty)

    init {
        allPostData.value = PostState.Loading
        getAllMedia()
    }

    sealed class PostState{
        object Empty : PostState()
        object Loading :PostState()
        data class Success(val data:List<PostViewData>):PostState()
    }

    fun getAllMedia() {
        viewModelScope.launch {
            val sb = StringBuilder()
            sb.append("https://instagram.com/").append(prefs.userName).append("/?__a=1")
            val url = sb.toString()
            when (val response = repository.getUserAllMedia(url, prefs.allCookie)) {
                is Resource.Success -> {
                    val dataList = ArrayList<PostViewData>()
                    val data = response.data?.graphql?.user?.edgeOwnerToTimelineMedia?.edges
                    data?.forEach { itData ->
                        val videoId = itData.node.id.toLongOrNull()
                        val displayUrl = itData.node.displayUrl
                        val likeCount = itData.node.edgeLikedBy.count.toLong()
                        val commentCount = itData.node.edgeMediaToComment.count.toLong()
                        dataList.add(
                            PostViewData(
                                mediaId = videoId,
                                imageUrl = displayUrl,
                                likeCount = likeCount,
                                commentCount = commentCount
                            )
                        )

                    }
                    allPostData.value = PostState.Success(dataList)


                }
            }
        }
    }

}