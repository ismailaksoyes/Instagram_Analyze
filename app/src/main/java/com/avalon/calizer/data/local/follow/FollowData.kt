package com.avalon.calizer.data.local.follow

data class FollowData(
    var uniqueType:Long? = null,
    var analyzeUserId:Long? = null ,
    var dsUserID: Long? = null ,
    var fullName: String?= null ,
    var hasAnonymousProfilePicture: Boolean? = null ,
    var isPrivate: Boolean? = null ,
    var isVerified: Boolean? = null ,
    var latestReelMedia: Int? = null ,
    var profilePicUrl: String? = null ,
    var profilePicId: String? = null ,
    var username: String?= null
)
