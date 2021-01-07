package com.avalon.avalon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [RoomData::class,FollowersData::class,FollowingData::class,LastFollowersData::class,LastFollowingData::class],version = 1,exportSchema = false)
abstract class MyDatabase :RoomDatabase(){

 abstract val roomDao:RoomDao
    companion object {
        @Volatile
        private var INSTANCE: MyDatabase?=null
        fun getInstance(context: Context): MyDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "data_database"
                    ).build()
                }
                return instance
            }

        }

    }

}