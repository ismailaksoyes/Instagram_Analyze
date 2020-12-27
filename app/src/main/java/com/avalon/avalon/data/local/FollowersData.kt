package com.avalon.avalon.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "followers_table")
data class FollowersData(
    @PrimaryKey(autoGenerate = true)
    val userId:Int,
   // val accountBadges: List<Any>,
    val fullName: String,
    val hasAnonymousProfilePicture: Boolean,
    val isPrivate: Boolean,
    val isVerified: Boolean,
    val latestReelMedia: Int,
    val pk: Long,
    val profilePicUrl: String,
    val profilePicId: String,
   // val storyReelMediaIds: List<Any>,
    val username: String
)
