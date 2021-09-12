package com.avalon.calizer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowingData
import com.avalon.calizer.data.local.follow.OldFollowersData
import com.avalon.calizer.data.local.follow.OldFollowingData
import com.avalon.calizer.data.local.profile.AccountsInfoData


@Database(
 entities = [ FollowersData::class,FollowingData::class,OldFollowersData::class,OldFollowingData::class, AccountsData::class,AccountsInfoData::class,RemoteKeys::class],
 version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract val roomDao: RoomDao

}