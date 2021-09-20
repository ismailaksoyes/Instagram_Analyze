package com.avalon.calizer.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApiResponseUserPk(
    @SerializedName("always_show_message_button_to_pro_account")
    val alwaysShowMessageButtonToProAccount: Boolean,
    @SerializedName("graphql")
    val graphql: Graphql,
    @SerializedName("logging_page_id")
    val loggingPageId: String,
    @SerializedName("profile_pic_edit_sync_props")
    val profilePicEditSyncProps: ProfilePicEditSyncProps,
    @SerializedName("seo_category_infos")
    val seoCategoryInfos: List<List<String>>,
    @SerializedName("show_follow_dialog")
    val showFollowDialog: Boolean,
    @SerializedName("show_suggested_profiles")
    val showSuggestedProfiles: Boolean,
    @SerializedName("show_view_shop")
    val showViewShop: Boolean,
    @SerializedName("toast_content_on_load")
    val toastContentOnLoad: Any
) {
    @Keep
    data class Graphql(
        @SerializedName("user")
        val user: User
    ) {
        @Keep
        data class User(
            @SerializedName("biography")
            val biography: String,
            @SerializedName("blocked_by_viewer")
            val blockedByViewer: Boolean,
            @SerializedName("business_address_json")
            val businessAddressJson: Any,
            @SerializedName("business_category_name")
            val businessCategoryName: String,
            @SerializedName("business_contact_method")
            val businessContactMethod: Any,
            @SerializedName("business_email")
            val businessEmail: Any,
            @SerializedName("business_phone_number")
            val businessPhoneNumber: Any,
            @SerializedName("category_enum")
            val categoryEnum: String,
            @SerializedName("category_name")
            val categoryName: Any,
            @SerializedName("connected_fb_page")
            val connectedFbPage: Any,
            @SerializedName("country_block")
            val countryBlock: Boolean,
            @SerializedName("edge_felix_video_timeline")
            val edgeFelixVideoTimeline: EdgeFelixVideoTimeline,
            @SerializedName("edge_follow")
            val edgeFollow: EdgeFollow,
            @SerializedName("edge_followed_by")
            val edgeFollowedBy: EdgeFollowedBy,
            @SerializedName("edge_media_collections")
            val edgeMediaCollections: EdgeMediaCollections,
            @SerializedName("edge_mutual_followed_by")
            val edgeMutualFollowedBy: EdgeMutualFollowedBy,
            @SerializedName("edge_owner_to_timeline_media")
            val edgeOwnerToTimelineMedia: EdgeOwnerToTimelineMedia,
            @SerializedName("edge_related_profiles")
            val edgeRelatedProfiles: EdgeRelatedProfiles,
            @SerializedName("edge_saved_media")
            val edgeSavedMedia: EdgeSavedMedia,
            @SerializedName("external_url")
            val externalUrl: String,
            @SerializedName("external_url_linkshimmed")
            val externalUrlLinkshimmed: String,
            @SerializedName("fbid")
            val fbid: String,
            @SerializedName("followed_by_viewer")
            val followedByViewer: Boolean,
            @SerializedName("follows_viewer")
            val followsViewer: Boolean,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("has_ar_effects")
            val hasArEffects: Boolean,
            @SerializedName("has_blocked_viewer")
            val hasBlockedViewer: Boolean,
            @SerializedName("has_channel")
            val hasChannel: Boolean,
            @SerializedName("has_clips")
            val hasClips: Boolean,
            @SerializedName("has_guides")
            val hasGuides: Boolean,
            @SerializedName("has_requested_viewer")
            val hasRequestedViewer: Boolean,
            @SerializedName("hide_like_and_view_counts")
            val hideLikeAndViewCounts: Boolean,
            @SerializedName("highlight_reel_count")
            val highlightReelCount: Int,
            @SerializedName("id")
            val id: String,
            @SerializedName("is_business_account")
            val isBusinessAccount: Boolean,
            @SerializedName("is_joined_recently")
            val isJoinedRecently: Boolean,
            @SerializedName("is_private")
            val isPrivate: Boolean,
            @SerializedName("is_professional_account")
            val isProfessionalAccount: Boolean,
            @SerializedName("is_verified")
            val isVerified: Boolean,
            @SerializedName("overall_category_name")
            val overallCategoryName: Any,
            @SerializedName("profile_pic_url")
            val profilePicUrl: String,
            @SerializedName("profile_pic_url_hd")
            val profilePicUrlHd: String,
            @SerializedName("pronouns")
            val pronouns: List<Any>,
            @SerializedName("requested_by_viewer")
            val requestedByViewer: Boolean,
            @SerializedName("restricted_by_viewer")
            val restrictedByViewer: Any,
            @SerializedName("should_show_category")
            val shouldShowCategory: Boolean,
            @SerializedName("should_show_public_contacts")
            val shouldShowPublicContacts: Boolean,
            @SerializedName("username")
            val username: String
        ) {
            @Keep
            data class EdgeFelixVideoTimeline(
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
                        @SerializedName("accessibility_caption")
                        val accessibilityCaption: Any,
                        @SerializedName("coauthor_producers")
                        val coauthorProducers: List<Any>,
                        @SerializedName("comments_disabled")
                        val commentsDisabled: Boolean,
                        @SerializedName("dash_info")
                        val dashInfo: DashInfo,
                        @SerializedName("dimensions")
                        val dimensions: Dimensions,
                        @SerializedName("display_url")
                        val displayUrl: String,
                        @SerializedName("edge_liked_by")
                        val edgeLikedBy: EdgeLikedBy,
                        @SerializedName("edge_media_preview_like")
                        val edgeMediaPreviewLike: EdgeMediaPreviewLike,
                        @SerializedName("edge_media_to_caption")
                        val edgeMediaToCaption: EdgeMediaToCaption,
                        @SerializedName("edge_media_to_comment")
                        val edgeMediaToComment: EdgeMediaToComment,
                        @SerializedName("edge_media_to_tagged_user")
                        val edgeMediaToTaggedUser: EdgeMediaToTaggedUser,
                        @SerializedName("encoding_status")
                        val encodingStatus: Any,
                        @SerializedName("fact_check_overall_rating")
                        val factCheckOverallRating: Any,
                        @SerializedName("fact_check_information")
                        val factCheckInformation: Any,
                        @SerializedName("felix_profile_grid_crop")
                        val felixProfileGridCrop: Any,
                        @SerializedName("gating_info")
                        val gatingInfo: Any,
                        @SerializedName("has_audio")
                        val hasAudio: Boolean,
                        @SerializedName("has_upcoming_event")
                        val hasUpcomingEvent: Boolean,
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("is_published")
                        val isPublished: Boolean,
                        @SerializedName("is_video")
                        val isVideo: Boolean,
                        @SerializedName("location")
                        val location: Any,
                        @SerializedName("media_overlay_info")
                        val mediaOverlayInfo: Any,
                        @SerializedName("media_preview")
                        val mediaPreview: String,
                        @SerializedName("owner")
                        val owner: Owner,
                        @SerializedName("product_type")
                        val productType: String,
                        @SerializedName("sharing_friction_info")
                        val sharingFrictionInfo: SharingFrictionInfo,
                        @SerializedName("shortcode")
                        val shortcode: String,
                        @SerializedName("taken_at_timestamp")
                        val takenAtTimestamp: Int,
                        @SerializedName("thumbnail_resources")
                        val thumbnailResources: List<ThumbnailResource>,
                        @SerializedName("thumbnail_src")
                        val thumbnailSrc: String,
                        @SerializedName("title")
                        val title: String,
                        @SerializedName("tracking_token")
                        val trackingToken: String,
                        @SerializedName("__typename")
                        val typename: String,
                        @SerializedName("video_duration")
                        val videoDuration: Double,
                        @SerializedName("video_url")
                        val videoUrl: String,
                        @SerializedName("video_view_count")
                        val videoViewCount: Int
                    ) {
                        @Keep
                        data class DashInfo(
                            @SerializedName("is_dash_eligible")
                            val isDashEligible: Boolean,
                            @SerializedName("number_of_qualities")
                            val numberOfQualities: Int,
                            @SerializedName("video_dash_manifest")
                            val videoDashManifest: Any
                        )

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
                        data class EdgeMediaToCaption(
                            @SerializedName("edges")
                            val edges: List<Edge>
                        ) {
                            @Keep
                            data class Edge(
                                @SerializedName("node")
                                val node: Node
                            ) {
                                @Keep
                                data class Node(
                                    @SerializedName("text")
                                    val text: String
                                )
                            }
                        }

                        @Keep
                        data class EdgeMediaToComment(
                            @SerializedName("count")
                            val count: Int
                        )

                        @Keep
                        data class EdgeMediaToTaggedUser(
                            @SerializedName("edges")
                            val edges: List<Any>
                        )

                        @Keep
                        data class Owner(
                            @SerializedName("id")
                            val id: String,
                            @SerializedName("username")
                            val username: String
                        )

                        @Keep
                        data class SharingFrictionInfo(
                            @SerializedName("bloks_app_url")
                            val bloksAppUrl: Any,
                            @SerializedName("should_have_sharing_friction")
                            val shouldHaveSharingFriction: Boolean
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

            @Keep
            data class EdgeFollow(
                @SerializedName("count")
                val count: Int
            )

            @Keep
            data class EdgeFollowedBy(
                @SerializedName("count")
                val count: Int
            )

            @Keep
            data class EdgeMediaCollections(
                @SerializedName("count")
                val count: Int,
                @SerializedName("edges")
                val edges: List<Any>,
                @SerializedName("page_info")
                val pageInfo: PageInfo
            ) {
                @Keep
                data class PageInfo(
                    @SerializedName("end_cursor")
                    val endCursor: Any,
                    @SerializedName("has_next_page")
                    val hasNextPage: Boolean
                )
            }

            @Keep
            data class EdgeMutualFollowedBy(
                @SerializedName("count")
                val count: Int,
                @SerializedName("edges")
                val edges: List<Any>
            )

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
                        @SerializedName("accessibility_caption")
                        val accessibilityCaption: Any,
                        @SerializedName("clips_music_attribution_info")
                        val clipsMusicAttributionInfo: Any,
                        @SerializedName("coauthor_producers")
                        val coauthorProducers: List<Any>,
                        @SerializedName("comments_disabled")
                        val commentsDisabled: Boolean,
                        @SerializedName("dash_info")
                        val dashInfo: DashInfo,
                        @SerializedName("dimensions")
                        val dimensions: Dimensions,
                        @SerializedName("display_url")
                        val displayUrl: String,
                        @SerializedName("edge_liked_by")
                        val edgeLikedBy: EdgeLikedBy,
                        @SerializedName("edge_media_preview_like")
                        val edgeMediaPreviewLike: EdgeMediaPreviewLike,
                        @SerializedName("edge_media_to_caption")
                        val edgeMediaToCaption: EdgeMediaToCaption,
                        @SerializedName("edge_media_to_comment")
                        val edgeMediaToComment: EdgeMediaToComment,
                        @SerializedName("edge_media_to_tagged_user")
                        val edgeMediaToTaggedUser: EdgeMediaToTaggedUser,
                        @SerializedName("edge_sidecar_to_children")
                        val edgeSidecarToChildren: EdgeSidecarToChildren,
                        @SerializedName("fact_check_overall_rating")
                        val factCheckOverallRating: Any,
                        @SerializedName("fact_check_information")
                        val factCheckInformation: Any,
                        @SerializedName("felix_profile_grid_crop")
                        val felixProfileGridCrop: Any,
                        @SerializedName("gating_info")
                        val gatingInfo: Any,
                        @SerializedName("has_audio")
                        val hasAudio: Boolean,
                        @SerializedName("has_upcoming_event")
                        val hasUpcomingEvent: Boolean,
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("is_video")
                        val isVideo: Boolean,
                        @SerializedName("location")
                        val location: Location,
                        @SerializedName("media_overlay_info")
                        val mediaOverlayInfo: Any,
                        @SerializedName("media_preview")
                        val mediaPreview: String,
                        @SerializedName("owner")
                        val owner: Owner,
                        @SerializedName("product_type")
                        val productType: String,
                        @SerializedName("sharing_friction_info")
                        val sharingFrictionInfo: SharingFrictionInfo,
                        @SerializedName("shortcode")
                        val shortcode: String,
                        @SerializedName("taken_at_timestamp")
                        val takenAtTimestamp: Int,
                        @SerializedName("thumbnail_resources")
                        val thumbnailResources: List<ThumbnailResource>,
                        @SerializedName("thumbnail_src")
                        val thumbnailSrc: String,
                        @SerializedName("tracking_token")
                        val trackingToken: String,
                        @SerializedName("__typename")
                        val typename: String,
                        @SerializedName("video_url")
                        val videoUrl: String,
                        @SerializedName("video_view_count")
                        val videoViewCount: Int
                    ) {
                        @Keep
                        data class DashInfo(
                            @SerializedName("is_dash_eligible")
                            val isDashEligible: Boolean,
                            @SerializedName("number_of_qualities")
                            val numberOfQualities: Int,
                            @SerializedName("video_dash_manifest")
                            val videoDashManifest: Any
                        )

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
                        data class EdgeMediaToCaption(
                            @SerializedName("edges")
                            val edges: List<Edge>
                        ) {
                            @Keep
                            data class Edge(
                                @SerializedName("node")
                                val node: Node
                            ) {
                                @Keep
                                data class Node(
                                    @SerializedName("text")
                                    val text: String
                                )
                            }
                        }

                        @Keep
                        data class EdgeMediaToComment(
                            @SerializedName("count")
                            val count: Int
                        )

                        @Keep
                        data class EdgeMediaToTaggedUser(
                            @SerializedName("edges")
                            val edges: List<Edge>
                        ) {
                            @Keep
                            data class Edge(
                                @SerializedName("node")
                                val node: Node
                            ) {
                                @Keep
                                data class Node(
                                    @SerializedName("user")
                                    val user: User,
                                    @SerializedName("x")
                                    val x: Double,
                                    @SerializedName("y")
                                    val y: Double
                                ) {
                                    @Keep
                                    data class User(
                                        @SerializedName("followed_by_viewer")
                                        val followedByViewer: Boolean,
                                        @SerializedName("full_name")
                                        val fullName: String,
                                        @SerializedName("id")
                                        val id: String,
                                        @SerializedName("is_verified")
                                        val isVerified: Boolean,
                                        @SerializedName("profile_pic_url")
                                        val profilePicUrl: String,
                                        @SerializedName("username")
                                        val username: String
                                    )
                                }
                            }
                        }

                        @Keep
                        data class EdgeSidecarToChildren(
                            @SerializedName("edges")
                            val edges: List<Edge>
                        ) {
                            @Keep
                            data class Edge(
                                @SerializedName("node")
                                val node: Node
                            ) {
                                @Keep
                                data class Node(
                                    @SerializedName("accessibility_caption")
                                    val accessibilityCaption: String,
                                    @SerializedName("dimensions")
                                    val dimensions: Dimensions,
                                    @SerializedName("display_url")
                                    val displayUrl: String,
                                    @SerializedName("edge_media_to_tagged_user")
                                    val edgeMediaToTaggedUser: EdgeMediaToTaggedUser,
                                    @SerializedName("fact_check_overall_rating")
                                    val factCheckOverallRating: Any,
                                    @SerializedName("fact_check_information")
                                    val factCheckInformation: Any,
                                    @SerializedName("gating_info")
                                    val gatingInfo: Any,
                                    @SerializedName("has_upcoming_event")
                                    val hasUpcomingEvent: Boolean,
                                    @SerializedName("id")
                                    val id: String,
                                    @SerializedName("is_video")
                                    val isVideo: Boolean,
                                    @SerializedName("media_overlay_info")
                                    val mediaOverlayInfo: Any,
                                    @SerializedName("media_preview")
                                    val mediaPreview: String,
                                    @SerializedName("owner")
                                    val owner: Owner,
                                    @SerializedName("sharing_friction_info")
                                    val sharingFrictionInfo: SharingFrictionInfo,
                                    @SerializedName("shortcode")
                                    val shortcode: String,
                                    @SerializedName("__typename")
                                    val typename: String
                                ) {
                                    @Keep
                                    data class Dimensions(
                                        @SerializedName("height")
                                        val height: Int,
                                        @SerializedName("width")
                                        val width: Int
                                    )

                                    @Keep
                                    data class EdgeMediaToTaggedUser(
                                        @SerializedName("edges")
                                        val edges: List<Edge>
                                    ) {
                                        @Keep
                                        data class Edge(
                                            @SerializedName("node")
                                            val node: Node
                                        ) {
                                            @Keep
                                            data class Node(
                                                @SerializedName("user")
                                                val user: User,
                                                @SerializedName("x")
                                                val x: Double,
                                                @SerializedName("y")
                                                val y: Double
                                            ) {
                                                @Keep
                                                data class User(
                                                    @SerializedName("followed_by_viewer")
                                                    val followedByViewer: Boolean,
                                                    @SerializedName("full_name")
                                                    val fullName: String,
                                                    @SerializedName("id")
                                                    val id: String,
                                                    @SerializedName("is_verified")
                                                    val isVerified: Boolean,
                                                    @SerializedName("profile_pic_url")
                                                    val profilePicUrl: String,
                                                    @SerializedName("username")
                                                    val username: String
                                                )
                                            }
                                        }
                                    }

                                    @Keep
                                    data class Owner(
                                        @SerializedName("id")
                                        val id: String,
                                        @SerializedName("username")
                                        val username: String
                                    )

                                    @Keep
                                    data class SharingFrictionInfo(
                                        @SerializedName("bloks_app_url")
                                        val bloksAppUrl: Any,
                                        @SerializedName("should_have_sharing_friction")
                                        val shouldHaveSharingFriction: Boolean
                                    )
                                }
                            }
                        }

                        @Keep
                        data class Location(
                            @SerializedName("has_public_page")
                            val hasPublicPage: Boolean,
                            @SerializedName("id")
                            val id: String,
                            @SerializedName("name")
                            val name: String,
                            @SerializedName("slug")
                            val slug: String
                        )

                        @Keep
                        data class Owner(
                            @SerializedName("id")
                            val id: String,
                            @SerializedName("username")
                            val username: String
                        )

                        @Keep
                        data class SharingFrictionInfo(
                            @SerializedName("bloks_app_url")
                            val bloksAppUrl: Any,
                            @SerializedName("should_have_sharing_friction")
                            val shouldHaveSharingFriction: Boolean
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

            @Keep
            data class EdgeRelatedProfiles(
                @SerializedName("edges")
                val edges: List<Edge>
            ) {
                @Keep
                data class Edge(
                    @SerializedName("node")
                    val node: Node
                ) {
                    @Keep
                    data class Node(
                        @SerializedName("full_name")
                        val fullName: String,
                        @SerializedName("id")
                        val id: String,
                        @SerializedName("is_private")
                        val isPrivate: Boolean,
                        @SerializedName("is_verified")
                        val isVerified: Boolean,
                        @SerializedName("profile_pic_url")
                        val profilePicUrl: String,
                        @SerializedName("username")
                        val username: String
                    )
                }
            }

            @Keep
            data class EdgeSavedMedia(
                @SerializedName("count")
                val count: Int,
                @SerializedName("edges")
                val edges: List<Any>,
                @SerializedName("page_info")
                val pageInfo: PageInfo
            ) {
                @Keep
                data class PageInfo(
                    @SerializedName("end_cursor")
                    val endCursor: Any,
                    @SerializedName("has_next_page")
                    val hasNextPage: Boolean
                )
            }
        }
    }

    @Keep
    data class ProfilePicEditSyncProps(
        @SerializedName("change_profile_pic_actions_screen_cancel_cta")
        val changeProfilePicActionsScreenCancelCta: List<String>,
        @SerializedName("change_profile_pic_actions_screen_header")
        val changeProfilePicActionsScreenHeader: List<String>,
        @SerializedName("change_profile_pic_actions_screen_remove_cta")
        val changeProfilePicActionsScreenRemoveCta: List<String>,
        @SerializedName("change_profile_pic_actions_screen_subheader")
        val changeProfilePicActionsScreenSubheader: List<String>,
        @SerializedName("change_profile_pic_actions_screen_upload_cta")
        val changeProfilePicActionsScreenUploadCta: List<String>,
        @SerializedName("identity_id")
        val identityId: String,
        @SerializedName("is_business_central_identity")
        val isBusinessCentralIdentity: Boolean,
        @SerializedName("remove_profile_pic_cancel_cta")
        val removeProfilePicCancelCta: Any,
        @SerializedName("remove_profile_pic_confirm_cta")
        val removeProfilePicConfirmCta: Any,
        @SerializedName("remove_profile_pic_header")
        val removeProfilePicHeader: Any,
        @SerializedName("remove_profile_pic_subtext")
        val removeProfilePicSubtext: Any,
        @SerializedName("show_change_profile_pic_confirm_dialog")
        val showChangeProfilePicConfirmDialog: Boolean,
        @SerializedName("show_profile_pic_sync_reminders")
        val showProfilePicSyncReminders: Boolean
    )
}