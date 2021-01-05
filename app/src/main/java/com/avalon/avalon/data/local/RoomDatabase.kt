package com.avalon.avalon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoomData::class,FollowersData::class,FollowingData::class,LastFollowersData::class,LastFollowingData::class],version = 1,exportSchema = false)
abstract class RoomDatabase :RoomDatabase(){

 abstract val roomDao:RoomDao
    companion object {
        @Volatile
        private var INSTANCE: com.avalon.avalon.data.local.RoomDatabase?=null
        fun getInstance(context: Context): com.avalon.avalon.data.local.RoomDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDatabase::class.java,
                        "data_database"
                    ).build()
                }
                return instance
            }

        }

    }

}