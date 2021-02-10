package com.avalon.calizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cookie_table")
data class RoomData(
        var csfr: String,
        val dsUserID: String,
        val rur: String,
        val sessID: String,
        val shbid: String,
        val shbts: String,
        val mid: String,
        val allCookie:String
){
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0
}