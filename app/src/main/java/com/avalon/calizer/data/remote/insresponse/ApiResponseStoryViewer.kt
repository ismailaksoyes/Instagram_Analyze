package com.avalon.calizer.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApiResponseStoryViewer(
    @SerializedName("next_max_id")
    val nextMaxId: String?,
    @SerializedName("reactions")
    val reactions: List<Any>,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_viewer_count")
    val totalViewerCount: Int,
    @SerializedName("updated_media")
    val updatedMedia: UpdatedMedia,
    @SerializedName("user_count")
    val userCount: Int,
    @SerializedName("users")
    val users: List<User>
) {
    @Keep
    data class UpdatedMedia(
        @SerializedName("boosted_status")
        val boostedStatus: String,
        @SerializedName("can_reply")
        val canReply: Boolean,
        @SerializedName("can_reshare")
        val canReshare: Boolean,
        @SerializedName("can_see_insights_as_brand")
        val canSeeInsightsAsBrand: Boolean,
        @SerializedName("can_send_custom_emojis")
        val canSendCustomEmojis: Boolean,
        @SerializedName("caption")
        val caption: Any,
        @SerializedName("caption_position")
        val captionPosition: Double,
        @SerializedName("caption_is_edited")
        val captionIsEdited: Boolean,
        @SerializedName("client_cache_key")
        val clientCacheKey: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("commerciality_status")
        val commercialityStatus: String,
        @SerializedName("deleted_reason")
        val deletedReason: Int,
        @SerializedName("device_timestamp")
        val deviceTimestamp: Long,
        @SerializedName("expiring_at")
        val expiringAt: Int,
        @SerializedName("fb_user_tags")
        val fbUserTags: FbUserTags,
        @SerializedName("fb_viewer_count")
        val fbViewerCount: Any,
        @SerializedName("filter_type")
        val filterType: Int,
        @SerializedName("has_shared_to_fb")
        val hasSharedToFb: Int,
        @SerializedName("has_shared_to_fb_dating")
        val hasSharedToFbDating: Int,
        @SerializedName("id")
        val id: String,
        @SerializedName("image_versions2")
        val imageVersions2: ImageVersions2,
        @SerializedName("imported_taken_at")
        val importedTakenAt: Int,
        @SerializedName("integrity_review_decision")
        val integrityReviewDecision: String,
        @SerializedName("is_commercial")
        val isCommercial: Boolean,
        @SerializedName("is_paid_partnership")
        val isPaidPartnership: Boolean,
        @SerializedName("is_reel_media")
        val isReelMedia: Boolean,
        @SerializedName("is_unified_video")
        val isUnifiedVideo: Boolean,
        @SerializedName("is_in_profile_grid")
        val isInProfileGrid: Boolean,
        @SerializedName("like_and_view_counts_disabled")
        val likeAndViewCountsDisabled: Boolean,
        @SerializedName("media_type")
        val mediaType: Int,
        @SerializedName("multi_author_reel_names")
        val multiAuthorReelNames: List<Any>,
        @SerializedName("music_metadata")
        val musicMetadata: Any,
        @SerializedName("organic_tracking_token")
        val organicTrackingToken: String,
        @SerializedName("original_height")
        val originalHeight: Int,
        @SerializedName("original_width")
        val originalWidth: Int,
        @SerializedName("photo_of_you")
        val photoOfYou: Boolean,
        @SerializedName("pk")
        val pk: Long,
        @SerializedName("playlist_eligibility")
        val playlistEligibility: Boolean,
        @SerializedName("product_type")
        val productType: String,
        @SerializedName("profile_grid_control_enabled")
        val profileGridControlEnabled: Boolean,
        @SerializedName("sharing_friction_info")
        val sharingFrictionInfo: SharingFrictionInfo,
        @SerializedName("show_one_tap_fb_share_tooltip")
        val showOneTapFbShareTooltip: Boolean,
        @SerializedName("source_type")
        val sourceType: Int,
        @SerializedName("story_static_models")
        val storyStaticModels: List<Any>,
        @SerializedName("story_is_saved_to_archive")
        val storyIsSavedToArchive: Boolean,
        @SerializedName("supports_reel_reactions")
        val supportsReelReactions: Boolean,
        @SerializedName("taken_at")
        val takenAt: Int,
        @SerializedName("timezone_offset")
        val timezoneOffset: Int,
        @SerializedName("total_viewer_count")
        val totalViewerCount: Int,
        @SerializedName("user")
        val user: User,
        @SerializedName("viewer_count")
        val viewerCount: Int,
        @SerializedName("viewer_cursor")
        val viewerCursor: Any,
        @SerializedName("viewers")
        val viewers: List<Viewer>
    ) {
        @Keep
        data class FbUserTags(
            @SerializedName("in")
            val inX: List<Any>
        )

        @Keep
        data class ImageVersions2(
            @SerializedName("candidates")
            val candidates: List<Candidate>
        ) {
            @Keep
            data class Candidate(
                @SerializedName("height")
                val height: Int,
                @SerializedName("scans_profile")
                val scansProfile: String,
                @SerializedName("url")
                val url: String,
                @SerializedName("width")
                val width: Int
            )
        }

        @Keep
        data class SharingFrictionInfo(
            @SerializedName("bloks_app_url")
            val bloksAppUrl: Any,
            @SerializedName("should_have_sharing_friction")
            val shouldHaveSharingFriction: Boolean
        )

        @Keep
        data class User(
            @SerializedName("account_badges")
            val accountBadges: List<Any>,
            @SerializedName("allowed_commenter_type")
            val allowedCommenterType: String,
            @SerializedName("can_boost_post")
            val canBoostPost: Boolean,
            @SerializedName("can_see_organic_insights")
            val canSeeOrganicInsights: Boolean,
            @SerializedName("fbid_v2")
            val fbidV2: Long,
            @SerializedName("follow_friction_type")
            val followFrictionType: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("has_anonymous_profile_picture")
            val hasAnonymousProfilePicture: Boolean,
            @SerializedName("interop_messaging_user_fbid")
            val interopMessagingUserFbid: Long,
            @SerializedName("is_private")
            val isPrivate: Boolean,
            @SerializedName("is_unpublished")
            val isUnpublished: Boolean,
            @SerializedName("is_verified")
            val isVerified: Boolean,
            @SerializedName("pk")
            val pk: Long,
            @SerializedName("profile_pic_url")
            val profilePicUrl: String,
            @SerializedName("profile_pic_id")
            val profilePicId: String,
            @SerializedName("reel_auto_archive")
            val reelAutoArchive: String,
            @SerializedName("show_insights_terms")
            val showInsightsTerms: Boolean,
            @SerializedName("username")
            val username: String
        )

        @Keep
        data class Viewer(
            @SerializedName("follow_friction_type")
            val followFrictionType: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("is_private")
            val isPrivate: Boolean,
            @SerializedName("is_verified")
            val isVerified: Boolean,
            @SerializedName("pk")
            val pk: Long,
            @SerializedName("profile_pic_url")
            val profilePicUrl: String,
            @SerializedName("profile_pic_id")
            val profilePicId: String,
            @SerializedName("username")
            val username: String
        )
    }

    @Keep
    data class User(
        @SerializedName("follow_friction_type")
        val followFrictionType: Int,
        @SerializedName("friendship_status")
        val friendshipStatus: FriendshipStatus,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("is_private")
        val isPrivate: Boolean,
        @SerializedName("is_verified")
        val isVerified: Boolean,
        @SerializedName("latest_besties_reel_media")
        val latestBestiesReelMedia: Int,
        @SerializedName("latest_reel_media")
        val latestReelMedia: Int,
        @SerializedName("pk")
        val pk: Long,
        @SerializedName("profile_pic_url")
        val profilePicUrl: String,
        @SerializedName("profile_pic_id")
        val profilePicId: String,
        @SerializedName("username")
        val username: String
    ) {
        @Keep
        data class FriendshipStatus(
            @SerializedName("followed_by")
            val followedBy: Boolean,
            @SerializedName("following")
            val following: Boolean,
            @SerializedName("incoming_request")
            val incomingRequest: Boolean,
            @SerializedName("is_bestie")
            val isBestie: Boolean,
            @SerializedName("is_feed_favorite")
            val isFeedFavorite: Boolean,
            @SerializedName("is_private")
            val isPrivate: Boolean,
            @SerializedName("is_restricted")
            val isRestricted: Boolean,
            @SerializedName("outgoing_request")
            val outgoingRequest: Boolean
        )
    }
}