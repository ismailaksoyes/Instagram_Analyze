package com.avalon.calizer.data.api

import com.avalon.calizer.data.remote.insrequest.ApiTranslation
import com.avalon.calizer.data.remote.insresponse.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST()
    suspend fun getTranslation(
        @Url url:String,
        @Body lang:ApiTranslation
    ):Response<HashMap<String,String>>

    @GET("friendships/{userId}/followers/")
    suspend fun getFollowers(
        @Path("userId") userId:Long,
        @Query("max_id")maxId: String?,
        @Query("rank_token")rnkToken: String?,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseUserFollow>
    @GET("friendships/{userId}/following/")
    suspend fun getFollowing(
        @Path("userId") userId:Long,
        @Query("max_id")maxId: String?,
        @Query("rank_token")rnkToken: String?,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseUserFollow>
    @GET("feed/reels_tray/")
   suspend fun getReelsTray(
        @Header("Cookie") cookies:String?
    ):Response<ApiResponseReelsTray>

   @GET("feed/user/{userId}/story/")
   suspend fun getStory(
       @Path("userId") userId:Long,
       @Header("Cookie") cookies:String?
   ):Response<ApiResponseStory>

   @GET("feed/user/{userId}/")
   suspend fun getFeedUser(
       @Path("userId") userId:Long,
       @Query("max_id")maxId: String?,
       @Header("Cookie") cookies: String?
   )

    @GET("users/{userId}/info/")
    suspend fun getUserDetails(
        @Path("userId") userId:Long,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseUserDetails>

    @GET("friendships/set_reel_block_status/")
    suspend fun getBlockStatus(
        //params userlist
    ):Response<String>
    @GET("friendships/remove_follower/{user_id}/")
    suspend fun removeFollower(
        @Path("userId") userId:Long,
        @Header("Cookie") cookies: String?
    )

    @GET("friendships/create/{user_id}/")
    suspend fun addFollower(
        @Path("userId") userId:Long,
        @Header("Cookie") cookies: String?
    )

    @GET("users/blocked_list/")
    suspend fun getBlockedList(
        @Header("Cookie") cookies: String?
    )

    @GET("users/{userName}/usernameinfo/")
    suspend fun getUserInfo(
        @Path("userName") userName:String,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseUserPk>

    @GET("feed/user/{userId}/reel_media/")
    suspend fun getUserReelsMedia(
        @Path("userId") userId:Long,
        @Header("Cookie") cookies: String?
    )

    @GET("friendships/show/{userId}/")
    suspend fun getFriendShipStatusShow(
        @Path("userId") userId:Long,
        @Header("Cookie") cookies: String?
    )

    @GET("highlights/{userId}/highlights_tray/")
    suspend fun getHighlights(
        @Path("userId") userId:Long,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseHighlights>

    @GET("feed/reels_media/")
    suspend fun getHighlightsStory(
        @Query("reel_ids") highlightId:String,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseHighlightsStory>

    @GET("media/{storyId}/list_reel_media_viewer")
    suspend fun getStoryViewer(
        @Path("storyId") storyId:String,
        @Header("Cookie") cookies: String?,
        @Query("max_id")maxId: String?
    ):Response<ApiResponseStoryViewer>

    @GET()
    suspend fun getUserAllMedia(
        @Url url:String,
        @Header("Cookie") cookies: String?
    ):Response<ApiResponseUserMedia>



}