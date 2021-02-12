package com.avalon.calizer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
 entities = [RoomData::class, FollowersData::class, FollowingData::class, LastFollowersData::class, LastFollowingData::class, AccountsData::class],
 version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract val roomDao: RoomDao

}