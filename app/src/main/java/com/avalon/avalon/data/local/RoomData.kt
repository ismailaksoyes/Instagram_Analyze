package com.avalon.avalon.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cookie_table")
data class RoomData(
        @PrimaryKey(autoGenerate = true)
        val cookieId: Int,
        var csfr: String,
        val dsUserID: String,
       // val igDId: String,
        val rur: String,
        val sessID: String,
        val shbid: String,
        val shbts: String,
        val mid: String,
        val allCookie:String
)