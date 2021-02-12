package com.avalon.calizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cookie_table")
data class RoomData(
        var csfr: String,
        var dsUserID: String,
        var rur: String,
        var sessID: String,
        var shbid: String,
        var shbts: String,
        var mid: String,
        var allCookie:String
){
        @PrimaryKey(autoGenerate = true)
        var usernumber: Int? = null
}