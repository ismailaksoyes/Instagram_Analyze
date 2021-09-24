package com.avalon.calizer.data.remote.insresponse


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ApiResponseUserPk(
    @SerializedName("status")
    val status: String,
    @SerializedName("user")
    val user: User
) {
    @Keep
    data class User(
        @SerializedName("account_badges")
        val accountBadges: List<Any>,
        @SerializedName("account_type")
        val accountType: Int,
        @SerializedName("address_street")
        val addressStreet: String,
        @SerializedName("biography")
        val biography: String,
        @SerializedName("business_contact_method")
        val businessContactMethod: String,
        @SerializedName("can_hide_category")
        val canHideCategory: Boolean,
        @SerializedName("can_hide_public_contacts")
        val canHidePublicContacts: Boolean,
        @SerializedName("category")
        val category: String,
        @SerializedName("city_name")
        val cityName: String,
        @SerializedName("city_id")
        val cityId: Int,
        @SerializedName("contact_phone_number")
        val contactPhoneNumber: String,
        @SerializedName("direct_messaging")
        val directMessaging: String,
        @SerializedName("displayed_action_button_partner")
        val displayedActionButtonPartner: Any,
        @SerializedName("displayed_action_button_type")
        val displayedActionButtonType: String,
        @SerializedName("external_lynx_url")
        val externalLynxUrl: String,
        @SerializedName("external_url")
        val externalUrl: String,
        @SerializedName("fb_page_call_to_action_id")
        val fbPageCallToActionId: String,
        @SerializedName("follow_friction_type")
        val followFrictionType: Int,
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
        @SerializedName("has_guides")
        val hasGuides: Boolean,
        @SerializedName("has_igtv_series")
        val hasIgtvSeries: Boolean,
        @SerializedName("hd_profile_pic_url_info")
        val hdProfilePicUrlInfo: HdProfilePicUrlInfo,
        @SerializedName("hd_profile_pic_versions")
        val hdProfilePicVersions: List<HdProfilePicVersion>,
        @SerializedName("instagram_location_id")
        val instagramLocationId: String,
        @SerializedName("is_business")
        val isBusiness: Boolean,
        @SerializedName("is_call_to_action_enabled")
        val isCallToActionEnabled: Boolean,
        @SerializedName("is_eligible_for_smb_support_flow")
        val isEligibleForSmbSupportFlow: Boolean,
        @SerializedName("is_favorite")
        val isFavorite: Boolean,
        @SerializedName("is_private")
        val isPrivate: Boolean,
        @SerializedName("is_verified")
        val isVerified: Boolean,
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("longitude")
        val longitude: Double,
        @SerializedName("media_count")
        val mediaCount: Int,
        @SerializedName("mutual_followers_count")
        val mutualFollowersCount: Int,
        @SerializedName("pk")
        val pk: Long,
        @SerializedName("professional_conversion_suggested_account_type")
        val professionalConversionSuggestedAccountType: Int,
        @SerializedName("profile_context")
        val profileContext: String,
        @SerializedName("profile_context_links_with_user_ids")
        val profileContextLinksWithUserIds: List<ProfileContextLinksWithUserId>,
        @SerializedName("profile_context_mutual_follow_ids")
        val profileContextMutualFollowIds: List<String>,
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
        @SerializedName("should_show_category")
        val shouldShowCategory: Boolean,
        @SerializedName("should_show_public_contacts")
        val shouldShowPublicContacts: Boolean,
        @SerializedName("smb_delivery_partner")
        val smbDeliveryPartner: Any,
        @SerializedName("smb_get_quote_partner")
        val smbGetQuotePartner: Any,
        @SerializedName("smb_support_delivery_partner")
        val smbSupportDeliveryPartner: Any,
        @SerializedName("total_clips_count")
        val totalClipsCount: Int,
        @SerializedName("total_igtv_videos")
        val totalIgtvVideos: Int,
        @SerializedName("username")
        val username: String,
        @SerializedName("usertags_count")
        val usertagsCount: Int,
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

        @Keep
        data class ProfileContextLinksWithUserId(
            @SerializedName("end")
            val end: Int,
            @SerializedName("start")
            val start: Int,
            @SerializedName("username")
            val username: String
        )
    }
}