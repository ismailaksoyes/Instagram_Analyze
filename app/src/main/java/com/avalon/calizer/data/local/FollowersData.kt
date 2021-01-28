package com.avalon.calizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followers_table")
data class FollowersData(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0L,
    var fullName: String = "",
    var hasAnonymousProfilePicture: Boolean = false,
    var isPrivate: Boolean = false,
    var isVerified: Boolean = false,
    var latestReelMedia: Int = 0,
    var profilePicUrl: String = "",
    var profilePicId: String = "",
    var username: String = ""
)
