package com.avalon.avalon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "accounts_table")
data class AccountsData(
    @PrimaryKey(autoGenerate = true)
    var pk: Long = -1L,
    var userName:String= "",
    var profilePic:String = "",
    var csfr: String = "",
    var dsUserID: String = "",
    var rur: String = "",
    var sessID: String = "",
    var shbid: String = "",
    var shbts: String = "",
    var mid: String = "",
    var allCookie:String = ""

)
