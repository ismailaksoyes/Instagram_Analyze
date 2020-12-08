package com.avalon.avalon.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CookieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCookie(cookieData: CookieData)

    @Query("SELECT * FROM cookie_table WHERE cookieId=1")
    fun readAllData():LiveData<CookieData>
}