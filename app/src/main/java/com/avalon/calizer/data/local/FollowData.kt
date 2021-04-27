package com.avalon.calizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "follow_table")
data class FollowData(
    @PrimaryKey(autoGenerate = true)
    var pk: Long? ,
    var type:Long? ,
    var analyzeUserId:Long? ,
    var dsUserID: Long? ,
    var fullName: String?,
    var hasAnonymousProfilePicture: Boolean? ,
    var isPrivate: Boolean? ,
    var isVerified: Boolean? ,
    var latestReelMedia: Int? ,
    var profilePicUrl: String? ,
    var profilePicId: String? ,
    var username: String?
)
