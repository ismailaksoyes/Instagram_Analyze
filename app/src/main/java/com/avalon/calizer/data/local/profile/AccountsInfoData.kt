package com.avalon.calizer.data.local.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts_info")
data class AccountsInfoData(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 1L,
    var userName:String?= "",
    var profilePic:String? = "",
    var followers:Long? = -1L,
    var following:Long? = -1L,
    var posts:Long? = -1L,
    var userId:Long? = -1L,
    var followersType:Long? = 0L,
    var followingType:Long? = 2L
)
