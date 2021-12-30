package com.avalon.calizer.data.remote.insresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResponseUserMedia(
    @SerializedName("graphql")
    val graphql: Graphql
) {
    @Keep
    data class Graphql(
        @SerializedName("user")
        val user: User
    ) {
        @Keep
        data class User(
            @SerializedName("edge_owner_to_timeline_media")
            val edgeOwnerToTimelineMedia: EdgeOwnerToTimelineMedia
        ) {


            @Keep
            data class EdgeOwnerToTimelineMedia(
                @SerializedName("count")
                val count: Int,
                @SerializedName("edges")
                val edges: List<Edge>,
                @SerializedName("page_info")
                val pageInfo: PageInfo
            ) {
                @Keep
                data class Edge(
                    @SerializedName("node")
                    val node: Node
                ) {
                    @Keep
                    data class Node(
                        @SerializedName("comments_disabled")
                        val commentsDisabled: Boolean,
                        @SerializedName("dimensions")
                        val dimensions: Dimensions,
                        @SerializedName("display_url")
                        val displayUrl: String,
                        @SerializedName("edge_liked_by")
                        val edgeLikedBy: EdgeLikedBy,
                        @SerializedName("edge_media_preview_like")
                        val edgeMediaPreviewLike: EdgeMediaPreviewLike,
                        @SerializedName("edge_media_to_comment")
                        val edgeMediaToComment: EdgeMediaToComment,
                        @SerializedName("has_audio")
                        val hasAudio: Boolean,
                        @SerializedName("has_upcoming_event")
                        val hasUpcomingEvent: Boolean,
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("is_video")
                        val isVideo: Boolean,
                        @SerializedName("media_preview")
                        val mediaPreview: String,
                        @SerializedName("owner")
                        val owner: Owner,
                        @SerializedName("shortcode")
                        val shortcode: String,
                        @SerializedName("thumbnail_resources")
                        val thumbnailResources: List<ThumbnailResource>,
                        @SerializedName("thumbnail_src")
                        val thumbnailSrc: String,
                        @SerializedName("__typename")
                        val typename: String,
                        @SerializedName("video_url")
                        val videoUrl: String,
                        @SerializedName("video_view_count")
                        val videoViewCount: Int
                    ) {

                        @Keep
                        data class Dimensions(
                            @SerializedName("height")
                            val height: Int,
                            @SerializedName("width")
                            val width: Int
                        )

                        @Keep
                        data class EdgeLikedBy(
                            @SerializedName("count")
                            val count: Int
                        )

                        @Keep
                        data class EdgeMediaPreviewLike(
                            @SerializedName("count")
                            val count: Int
                        )


                        @Keep
                        data class EdgeMediaToComment(
                            @SerializedName("count")
                            val count: Int
                        )



                        @Keep
                        data class Owner(
                            @SerializedName("id")
                            val id: String,
                            @SerializedName("username")
                            val username: String
                        )


                        @Keep
                        data class ThumbnailResource(
                            @SerializedName("config_height")
                            val configHeight: Int,
                            @SerializedName("config_width")
                            val configWidth: Int,
                            @SerializedName("src")
                            val src: String
                        )
                    }
                }

                @Keep
                data class PageInfo(
                    @SerializedName("end_cursor")
                    val endCursor: String,
                    @SerializedName("has_next_page")
                    val hasNextPage: Boolean
                )
            }

        }
    }

}