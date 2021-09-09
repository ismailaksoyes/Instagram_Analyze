package com.avalon.calizer.data.local.follow

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "old_following_table",indices = [Index(value = arrayOf("uniqueType") ,unique = true)])
data class OldFollowingData(
    var uid:Long? = null,
    @PrimaryKey
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


