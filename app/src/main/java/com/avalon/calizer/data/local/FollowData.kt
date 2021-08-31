package com.avalon.calizer.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "follow_table",indices = [Index(value = arrayOf("uniqueType") ,unique = true)])
data class FollowData(
    @PrimaryKey(autoGenerate = true)
    var uid:Long?=null,
    var type:Long? = null ,
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
