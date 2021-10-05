package com.avalon.calizer.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApiResponseReelsTray(
    @SerializedName("broadcasts")
    val broadcasts: List<Any>,
    @SerializedName("emoji_reactions_config")
    val emojiReactionsConfig: EmojiReactionsConfig,
    @SerializedName("face_filter_nux_version")
    val faceFilterNuxVersion: Int,
    @SerializedName("has_new_nux_story")
    val hasNewNuxStory: Boolean,
    @SerializedName("nudge_story_reaction_ufi")
    val nudgeStoryReactionUfi: Boolean,
    @SerializedName("refresh_window_ms")
    val refreshWindowMs: Int,
    @SerializedName("response_timestamp")
    val responseTimestamp: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("sticker_version")
    val stickerVersion: Int,
    @SerializedName("stories_viewer_gestures_nux_eligible")
    val storiesViewerGesturesNuxEligible: Boolean,
    @SerializedName("story_ranking_token")
    val storyRankingToken: String,
    @SerializedName("tray")
    val tray: List<Tray>
) {
    @Keep
    data class EmojiReactionsConfig(
        @SerializedName("composer_nux_type")
        val composerNuxType: Int,
        @SerializedName("delivery_type")
        val deliveryType: Int,
        @SerializedName("hide_story_view_count")
        val hideStoryViewCount: Boolean,
        @SerializedName("overlay_skin_tone_picker_enabled")
        val overlaySkinTonePickerEnabled: Boolean,
        @SerializedName("persistent_self_story_badge_enabled")
        val persistentSelfStoryBadgeEnabled: Boolean,
        @SerializedName("presence_dot_enabled")
        val presenceDotEnabled: Boolean,
        @SerializedName("reaction_tray_interactive_panning_enabled")
        val reactionTrayInteractivePanningEnabled: Boolean,
        @SerializedName("self_story_badging_enabled")
        val selfStoryBadgingEnabled: Boolean,
        @SerializedName("swipe_up_to_show_reactions")
        val swipeUpToShowReactions: Boolean,
        @SerializedName("tappable_reply_composer_enabled")
        val tappableReplyComposerEnabled: Boolean,
        @SerializedName("ufi_type")
        val ufiType: Int
    )

    @Keep
    data class Tray(
        @SerializedName("can_gif_quick_reply")
        val canGifQuickReply: Boolean,
        @SerializedName("can_reply")
        val canReply: Boolean,
        @SerializedName("can_reshare")
        val canReshare: Boolean,
        @SerializedName("client_prefetch_score")
        val clientPrefetchScore: Double,
        @SerializedName("expiring_at")
        val expiringAt: Int,
        @SerializedName("has_besties_media")
        val hasBestiesMedia: Boolean,
        @SerializedName("has_pride_media")
        val hasPrideMedia: Boolean,
        @SerializedName("has_video")
        val hasVideo: Boolean,
        @SerializedName("id")
        val id: Long,
        @SerializedName("is_sensitive_vertical_ad")
        val isSensitiveVerticalAd: Boolean,
        @SerializedName("items")
        val items: List<Item>,
        @SerializedName("latest_besties_reel_media")
        val latestBestiesReelMedia: Double,
        @SerializedName("latest_reel_media")
        val latestReelMedia: Int,
        @SerializedName("media_count")
        val mediaCount: Int,
        @SerializedName("muted")
        val muted: Boolean,
        @SerializedName("prefetch_count")
        val prefetchCount: Int,
        @SerializedName("ranked_position")
        val rankedPosition: Int,
        @SerializedName("ranker_scores")
        val rankerScores: RankerScores,
        @SerializedName("reel_type")
        val reelType: String,
        @SerializedName("seen")
        val seen: Int,
        @SerializedName("seen_ranked_position")
        val seenRankedPosition: Int,
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
            @SerializedName("creative_config")
            val creativeConfig: CreativeConfig,
            @SerializedName("deleted_reason")
            val deletedReason: Int,
            @SerializedName("device_timestamp")
            val deviceTimestamp: Long,
            @SerializedName("expiring_at")
            val expiringAt: Int,
            @SerializedName("filter_type")
            val filterType: Int,
            @SerializedName("has_translation")
            val hasTranslation: Boolean,
            @SerializedName("id")
            val id: String,
            @SerializedName("image_versions2")
            val imageVersions2: ImageVersions2,
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
            @SerializedName("reel_mentions")
            val reelMentions: List<ReelMention>,
            @SerializedName("sharing_friction_info")
            val sharingFrictionInfo: SharingFrictionInfo,
            @SerializedName("show_one_tap_fb_share_tooltip")
            val showOneTapFbShareTooltip: Boolean,
            @SerializedName("story_static_models")
            val storyStaticModels: List<Any>,
            @SerializedName("supports_reel_reactions")
            val supportsReelReactions: Boolean,
            @SerializedName("taken_at")
            val takenAt: Int,
            @SerializedName("user")
            val user: User
        ) {
            @Keep
            data class CreativeConfig(
                @SerializedName("camera_facing")
                val cameraFacing: String,
                @SerializedName("capture_type")
                val captureType: String,
                @SerializedName("should_render_try_it_on")
                val shouldRenderTryItOn: Boolean
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
            data class ReelMention(
                @SerializedName("display_type")
                val displayType: String,
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
                @SerializedName("rotation")
                val rotation: Double,
                @SerializedName("user")
                val user: User,
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
                data class User(
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
            data class SharingFrictionInfo(
                @SerializedName("bloks_app_url")
                val bloksAppUrl: Any,
                @SerializedName("should_have_sharing_friction")
                val shouldHaveSharingFriction: Boolean
            )

            @Keep
            data class User(
                @SerializedName("is_private")
                val isPrivate: Boolean,
                @SerializedName("pk")
                val pk: Long
            )
        }

        @Keep
        data class RankerScores(
            @SerializedName("fp")
            val fp: Double,
            @SerializedName("ptap")
            val ptap: Double,
            @SerializedName("vm")
            val vm: Double
        )

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
                @SerializedName("following")
                val following: Boolean,
                @SerializedName("is_bestie")
                val isBestie: Boolean,
                @SerializedName("is_muting_reel")
                val isMutingReel: Boolean,
                @SerializedName("muting")
                val muting: Boolean,
                @SerializedName("outgoing_request")
                val outgoingRequest: Boolean
            )
        }
    }
}