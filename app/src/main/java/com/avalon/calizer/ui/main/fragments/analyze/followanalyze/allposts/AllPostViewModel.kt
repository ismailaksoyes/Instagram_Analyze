package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allposts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.avalon.calizer.data.local.analyze.PostUserData
import com.avalon.calizer.data.local.analyze.PostViewData
import com.avalon.calizer.data.local.follow.FollowRequestParams
import com.avalon.calizer.data.repository.FollowRepository
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.MySharedPreferences
import com.avalon.calizer.utils.Resource
import com.avalon.calizer.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class AllPostViewModel @Inject constructor(
    private val repository: Repository,
    private val followRepository: FollowRepository,
    private val prefs: MySharedPreferences
) : ViewModel() {


    val allPostData = MutableStateFlow<PostState>(PostState.Empty)

    private val _allAnalyzeMediaData = MutableStateFlow<PostMediaAnalyzeState>(PostMediaAnalyzeState.Empty)
    val allAnalyzeMediaData:StateFlow<PostMediaAnalyzeState> = _allAnalyzeMediaData

    init {
        allPostData.value = PostState.Loading
        getAllMedia()
    }

    sealed class PostMediaAnalyzeState{
        object Empty : PostMediaAnalyzeState()
        object Loading :PostMediaAnalyzeState()
        data class Success(val data:List<PostUserData>):PostMediaAnalyzeState()
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

    private fun likeUserAnalyze(userList:List<PostUserData>){
        userList.forEach { itData->
            Log.d("analyze-> ", "likeUserAnalyze: $itData ")
        }
    }

    private fun addLikeUserMediaLocal(userList:List<PostUserData>){
        viewModelScope.launch {
            followRepository.addMediaLikeUser(userList)
        }
    }

    private suspend fun getLikeUserMediaLocal():List<PostUserData>{
        return followRepository.getLikeUserMedia()
    }

     fun mostLikeAnalyze(postData:List<PostViewData>){
        viewModelScope.launch {
            val mostLikeTime = Date(prefs.mostLikeUpdateData)
            if (Utils.getTimeDifference(mostLikeTime)) {
                if (postData.isNotEmpty()){
                    val cacheData = arrayListOf<PostUserData>()
                    postData.forEach { postListItem->
                        delay((500 + (0..250).random()).toLong())
                        postListItem.mediaId?.let { itMediaId->
                            when(val response = repository.getMediaDetails(itMediaId,prefs.allCookie)){
                                is Resource.Success->{
                                    response.data?.users?.forEach { itUser->
                                        cacheData.add(
                                            PostUserData(
                                                analyzeUserId = prefs.selectedAccount,
                                                uniqueType = itUser.pk.toString().plus(itMediaId.toString().take(6)).toLong(),
                                                dsUserID = itUser.pk,
                                                mediaId = itMediaId,
                                                profilePicUrl = itUser.profilePicUrl,
                                                username = itUser.username
                                            )
                                        )
                                    }

                                }
                            }
                        }
                    }
                    addLikeUserMediaLocal(cacheData)
                    likeUserAnalyze(cacheData)
                }
            }else{
                val data = getLikeUserMediaLocal()
                likeUserAnalyze(data)
            }
        }

    }

}