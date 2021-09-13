package com.avalon.calizer.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApiResponseStory(
    @SerializedName("broadcast")
    val broadcast: Any,
    @SerializedName("reel")
    val reel: Reel,
    @SerializedName("status")
    val status: String
) {
    @Keep
    data class Reel(
        @SerializedName("can_gif_quick_reply")
        val canGifQuickReply: Boolean,
        @SerializedName("can_reply")
        val canReply: Boolean,
        @SerializedName("can_reshare")
        val canReshare: Boolean,
        @SerializedName("expiring_at")
        val expiringAt: Int,
        @SerializedName("has_besties_media")
        val hasBestiesMedia: Boolean,
        @SerializedName("id")
        val id: Long,
        @SerializedName("is_sensitive_vertical_ad")
        val isSensitiveVerticalAd: Boolean,
        @SerializedName("items")
        val items: List<Item>,
        @SerializedName("latest_reel_media")
        val latestReelMedia: Int,
        @SerializedName("media_count")
        val mediaCount: Int,
        @SerializedName("media_ids")
        val mediaIds: List<Long>,
        @SerializedName("prefetch_count")
        val prefetchCount: Int,
        @SerializedName("reel_type")
        val reelType: String,
        @SerializedName("seen")
        val seen: Int,
        @SerializedName("user")
        val user: User
    ) {
        @Keep
        data class Item(
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
            @SerializedName("filter_type")
            val filterType: Int,
            @SerializedName("has_audio")
            val hasAudio: Boolean,
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
            @SerializedName("lat")
            val lat: Double,
            @SerializedName("like_and_view_counts_disabled")
            val likeAndViewCountsDisabled: Boolean,
            @SerializedName("lng")
            val lng: Double,
            @SerializedName("location")
            val location: Location,
            @SerializedName("media_type")
            val mediaType: Int,
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
            @SerializedName("story_locations")
            val storyLocations: List<StoryLocation>,
            @SerializedName("story_static_models")
            val storyStaticModels: List<Any>,
            @SerializedName("supports_reel_reactions")
            val supportsReelReactions: Boolean,
            @SerializedName("taken_at")
            val takenAt: Int,
            @SerializedName("user")
            val user: User,
            @SerializedName("video_duration")
            val videoDuration: Double,
            @SerializedName("video_versions")
            val videoVersions: List<VideoVersion>
        ) {
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
            data class Location(
                @SerializedName("address")
                val address: String,
                @SerializedName("city")
                val city: String,
                @SerializedName("external_source")
                val externalSource: String,
                @SerializedName("facebook_places_id")
                val facebookPlacesId: Long,
                @SerializedName("has_viewer_saved")
                val hasViewerSaved: Any,
                @SerializedName("is_eligible_for_guides")
                val isEligibleForGuides: Boolean,
                @SerializedName("lat")
                val lat: Double,
                @SerializedName("lng")
                val lng: Double,
                @SerializedName("name")
                val name: String,
                @SerializedName("pk")
                val pk: Int,
                @SerializedName("short_name")
                val shortName: String
            )

            @Keep
            data class SharingFrictionInfo(
                @SerializedName("bloks_app_url")
                val bloksAppUrl: Any,
                @SerializedName("should_have_sharing_friction")
                val shouldHaveSharingFriction: Boolean
            )

            @Keep
            data class StoryLocation(
                @SerializedName("height")
                val height: Double,
                @SerializedName("is_fb_sticker")
                val isFbSticker: Int,
                @SerializedName("is_hidden")
                val isHidden: Int,
                @SerializedName("is_pinned")
                val isPinned: Int,
                @SerializedName("is_sticker")
                val isSticker: Int,
                @SerializedName("location")
                val location: Location,
                @SerializedName("rotation")
                val rotation: Double,
                @SerializedName("width")
                val width: Double,
                @SerializedName("x")
                val x: Double,
                @SerializedName("y")
                val y: Double,
                @SerializedName("z")
                val z: Int
            ) {
                @Keep
                data class Location(
                    @SerializedName("address")
                    val address: String,
                    @SerializedName("city")
                    val city: String,
                    @SerializedName("external_source")
                    val externalSource: String,
                    @SerializedName("facebook_places_id")
                    val facebookPlacesId: Long,
                    @SerializedName("has_viewer_saved")
                    val hasViewerSaved: Any,
                    @SerializedName("lat")
                    val lat: Double,
                    @SerializedName("lng")
                    val lng: Double,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("pk")
                    val pk: Int,
                    @SerializedName("short_name")
                    val shortName: String
                )
            }

            @Keep
            data class User(
                @SerializedName("account_badges")
                val accountBadges: List<Any>,
                @SerializedName("follow_friction_type")
                val followFrictionType: Int,
                @SerializedName("full_name")
                val fullName: String,
                @SerializedName("has_anonymous_profile_picture")
                val hasAnonymousProfilePicture: Boolean,
                @SerializedName("is_favorite")
                val isFavorite: Boolean,
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
                @SerializedName("username")
                val username: String
            )

            @Keep
            data class VideoVersion(
                @SerializedName("height")
                val height: Int,
                @SerializedName("id")
                val id: String,
                @SerializedName("type")
                val type: Int,
                @SerializedName("url")
                val url: String,
                @SerializedName("width")
                val width: Int
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
                @SerializedName("blocking")
                val blocking: Boolean,
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
                @SerializedName("muting")
                val muting: Boolean,
                @SerializedName("outgoing_request")
                val outgoingRequest: Boolean
            )
        }
    }
}