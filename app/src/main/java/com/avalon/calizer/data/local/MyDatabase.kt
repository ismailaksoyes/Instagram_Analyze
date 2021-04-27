package com.avalon.calizer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avalon.calizer.data.local.profile.AccountsInfoData


@Database(
 entities = [ FollowData::class, AccountsData::class,AccountsInfoData::class],
 version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract val roomDao: RoomDao

}