package com.avalon.avalon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_followers_table")
data class LastFollowersData(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0L,
    // val accountBadges: List<Any>,
    var fullName: String = "",
    var hasAnonymousProfilePicture: Boolean = false,
    var isPrivate: Boolean = false,
    var isVerified: Boolean = false,
    var latestReelMedia: Int = 0,
    var profilePicUrl: String = "",
    var profilePicId: String = "",
    // val storyReelMediaIds: List<Any>,
    var username: String = ""
)