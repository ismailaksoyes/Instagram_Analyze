package com.avalon.calizer.data.local.analyze

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "post_like_table",indices = [Index(value = arrayOf("mediaId") ,unique = true)])
data class PostUserData(
    @PrimaryKey(autoGenerate = true)
    var uid:Long? = null,
    var analyzeUserId:Long? = null ,
    var dsUserID: Long? = null ,
    var mediaId: Long?= null ,
    var profilePicUrl: String? = null ,
    var username: String?= null
)