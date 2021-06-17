package com.avalon.calizer.data.remote.insresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ApiResponseUserDetails(
    @SerializedName("status")
    val status: String,
    @SerializedName("user")
    val user: User
) {
    @Keep
    data class User(
        @SerializedName("about_your_account_bloks_entrypoint_enabled")
        val aboutYourAccountBloksEntrypointEnabled: Boolean,
        @SerializedName("account_badges")
        val accountBadges: List<Any>,
        @SerializedName("account_type")
        val accountType: Int,
        @SerializedName("address_street")
        val addressStreet: String,
        @SerializedName("ads_page_name")
        val adsPageName: Any,
        @SerializedName("ads_page_id")
        val adsPageId: Any,
        @SerializedName("aggregate_promote_engagement")
        val aggregatePromoteEngagement: Boolean,
        @SerializedName("allow_mention_setting")
        val allowMentionSetting: String,
        @SerializedName("allow_tag_setting")
        val allowTagSetting: String,
        @SerializedName("allowed_commenter_type")
        val allowedCommenterType: String,
        @SerializedName("auto_expand_chaining")
        val autoExpandChaining: Boolean,
        @SerializedName("besties_count")
        val bestiesCount: Int,
        @SerializedName("biography")
        val biography: String,
        @SerializedName("business_contact_method")
        val businessContactMethod: String,
        @SerializedName("can_be_reported_as_fraud")
        val canBeReportedAsFraud: Boolean,
        @SerializedName("can_be_tagged_as_sponsor")
        val canBeTaggedAsSponsor: Boolean,
        @SerializedName("can_boost_post")
        val canBoostPost: Boolean,
        @SerializedName("can_claim_page")
        val canClaimPage: Boolean,
        @SerializedName("can_convert_to_business")
        val canConvertToBusiness: Boolean,
        @SerializedName("can_create_new_standalone_fundraiser")
        val canCreateNewStandaloneFundraiser: Boolean,
        @SerializedName("can_create_new_standalone_personal_fundraiser")
        val canCreateNewStandalonePersonalFundraiser: Boolean,
        @SerializedName("can_create_sponsor_tags")
        val canCreateSponsorTags: Boolean,
        @SerializedName("can_crosspost_without_fb_token")
        val canCrosspostWithoutFbToken: Boolean,
        @SerializedName("can_follow_hashtag")
        val canFollowHashtag: Boolean,
        @SerializedName("can_hide_category")
        val canHideCategory: Boolean,
        @SerializedName("can_hide_public_contacts")
        val canHidePublicContacts: Boolean,
        @SerializedName("can_see_organic_insights")
        val canSeeOrganicInsights: Boolean,
        @SerializedName("can_see_primary_country_in_settings")
        val canSeePrimaryCountryInSettings: Boolean,
        @SerializedName("can_see_support_inbox")
        val canSeeSupportInbox: Boolean,
        @SerializedName("can_see_support_inbox_v1")
        val canSeeSupportInboxV1: Boolean,
        @SerializedName("can_tag_products_from_merchants")
        val canTagProductsFromMerchants: Boolean,
        @SerializedName("category")
        val category: String,
        @SerializedName("city_name")
        val cityName: String,
        @SerializedName("city_id")
        val cityId: Long,
        @SerializedName("contact_phone_number")
        val contactPhoneNumber: String,
        @SerializedName("direct_messaging")
        val directMessaging: String,
        @SerializedName("displayed_action_button_partner")
        val displayedActionButtonPartner: Any,
        @SerializedName("displayed_action_button_type")
        val displayedActionButtonType: String,
        @SerializedName("eligible_shopping_signup_entrypoints")
        val eligibleShoppingSignupEntrypoints: List<String>,
        @SerializedName("existing_user_age_collection_enabled")
        val existingUserAgeCollectionEnabled: Boolean,
        @SerializedName("external_url")
        val externalUrl: String,
        @SerializedName("fb_page_call_to_action_id")
        val fbPageCallToActionId: String,
        @SerializedName("fbid_v2")
        val fbidV2: Long,
        @SerializedName("fbpay_experience_enabled")
        val fbpayExperienceEnabled: Boolean,
        @SerializedName("feed_post_reshare_disabled")
        val feedPostReshareDisabled: Boolean,
        @SerializedName("follower_count")
        val followerCount: Int,
        @SerializedName("following_count")
        val followingCount: Int,
        @SerializedName("following_tag_count")
        val followingTagCount: Int,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("geo_media_count")
        val geoMediaCount: Int,
        @SerializedName("has_anonymous_profile_picture")
        val hasAnonymousProfilePicture: Boolean,
        @SerializedName("has_biography_translation")
        val hasBiographyTranslation: Boolean,
        @SerializedName("has_chaining")
        val hasChaining: Boolean,
        @SerializedName("has_eligible_whatsapp_linking_category")
        val hasEligibleWhatsappLinkingCategory: Boolean,
        @SerializedName("has_guides")
        val hasGuides: Boolean,
        @SerializedName("has_highlight_reels")
        val hasHighlightReels: Boolean,
        @SerializedName("has_placed_orders")
        val hasPlacedOrders: Boolean,
        @SerializedName("has_profile_video_feed")
        val hasProfileVideoFeed: Boolean,
        @SerializedName("has_saved_items")
        val hasSavedItems: Boolean,
        @SerializedName("hd_profile_pic_url_info")
        val hdProfilePicUrlInfo: HdProfilePicUrlInfo,
        @SerializedName("hd_profile_pic_versions")
        val hdProfilePicVersions: List<HdProfilePicVersion>,
        @SerializedName("highlight_reshare_disabled")
        val highlightReshareDisabled: Boolean,
        @SerializedName("include_direct_blacklist_status")
        val includeDirectBlacklistStatus: Boolean,
        @SerializedName("instagram_location_id")
        val instagramLocationId: String,
        @SerializedName("interop_messaging_user_fbid")
        val interopMessagingUserFbid: Long,
        @SerializedName("is_allowed_to_create_standalone_nonprofit_fundraisers")
        val isAllowedToCreateStandaloneNonprofitFundraisers: Boolean,
        @SerializedName("is_allowed_to_create_standalone_personal_fundraisers")
        val isAllowedToCreateStandalonePersonalFundraisers: Boolean,
        @SerializedName("is_auto_highlight_enabled")
        val isAutoHighlightEnabled: Boolean,
        @SerializedName("is_business")
        val isBusiness: Boolean,
        @SerializedName("is_call_to_action_enabled")
        val isCallToActionEnabled: Boolean,
        @SerializedName("is_eligible_for_appointment_creation_in_direct_thread")
        val isEligibleForAppointmentCreationInDirectThread: Boolean,
        @SerializedName("is_eligible_for_smb_support_flow")
        val isEligibleForSmbSupportFlow: Boolean,
        @SerializedName("is_eligible_to_show_fb_cross_sharing_nux")
        val isEligibleToShowFbCrossSharingNux: Boolean,
        @SerializedName("is_memorialized")
        val isMemorialized: Boolean,
        @SerializedName("is_needy")
        val isNeedy: Boolean,
        @SerializedName("is_potential_business")
        val isPotentialBusiness: Boolean,
        @SerializedName("is_private")
        val isPrivate: Boolean,
        @SerializedName("is_profile_action_needed")
        val isProfileActionNeeded: Boolean,
        @SerializedName("is_verified")
        val isVerified: Boolean,
        @SerializedName("is_video_creator")
        val isVideoCreator: Boolean,
        @SerializedName("is_interest_account")
        val isInterestAccount: Boolean,
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("longitude")
        val longitude: Double,
        @SerializedName("media_count")
        val mediaCount: Int,
        @SerializedName("num_of_admined_pages")
        val numOfAdminedPages: Int,
        @SerializedName("open_external_url_with_in_app_browser")
        val openExternalUrlWithInAppBrowser: Boolean,
        @SerializedName("page_name")
        val pageName: Any,
        @SerializedName("page_id")
        val pageId: Any,
        @SerializedName("pk")
        val pk: Long,
        @SerializedName("professional_conversion_suggested_account_type")
        val professionalConversionSuggestedAccountType: Int,
        @SerializedName("profile_pic_url")
        val profilePicUrl: String,
        @SerializedName("profile_pic_id")
        val profilePicId: String,
        @SerializedName("public_email")
        val publicEmail: String,
        @SerializedName("public_phone_country_code")
        val publicPhoneCountryCode: String,
        @SerializedName("public_phone_number")
        val publicPhoneNumber: String,
        @SerializedName("recently_bestied_by_count")
        val recentlyBestiedByCount: Int,
        @SerializedName("reel_auto_archive")
        val reelAutoArchive: String,
        @SerializedName("service_shop_merchant_entrypoint_app_id")
        val serviceShopMerchantEntrypointAppId: String,
        @SerializedName("service_shop_onboarding_status")
        val serviceShopOnboardingStatus: Any,
        @SerializedName("should_show_category")
        val shouldShowCategory: Boolean,
        @SerializedName("should_show_public_contacts")
        val shouldShowPublicContacts: Boolean,
        @SerializedName("show_besties_badge")
        val showBestiesBadge: Boolean,
        @SerializedName("show_conversion_edit_entry")
        val showConversionEditEntry: Boolean,
        @SerializedName("show_post_insights_entry_point")
        val showPostInsightsEntryPoint: Boolean,
        @SerializedName("show_insights_terms")
        val showInsightsTerms: Boolean,
        @SerializedName("smb_delivery_partner")
        val smbDeliveryPartner: Any,
        @SerializedName("smb_support_delivery_partner")
        val smbSupportDeliveryPartner: Any,
        @SerializedName("total_ar_effects")
        val totalArEffects: Int,
        @SerializedName("total_igtv_videos")
        val totalIgtvVideos: Int,
        @SerializedName("username")
        val username: String,
        @SerializedName("usertag_review_enabled")
        val usertagReviewEnabled: Boolean,
        @SerializedName("usertags_count")
        val usertagsCount: Int,
        @SerializedName("whatsapp_number")
        val whatsappNumber: String,
        @SerializedName("zip")
        val zip: String
    ) {
        @Keep
        data class HdProfilePicUrlInfo(
            @SerializedName("height")
            val height: Int,
            @SerializedName("url")
            val url: String,
            @SerializedName("width")
            val width: Int
        )

        @Keep
        data class HdProfilePicVersion(
            @SerializedName("height")
            val height: Int,
            @SerializedName("url")
            val url: String,
            @SerializedName("width")
            val width: Int
        )
    }
}
