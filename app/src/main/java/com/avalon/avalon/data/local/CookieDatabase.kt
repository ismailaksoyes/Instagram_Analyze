package com.avalon.avalon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CookieData::class],version = 1,exportSchema = false)
abstract class CookieDatabase :RoomDatabase(){

 abstract val cookieDao:CookieDao
    companion object {
        @Volatile
        private var INSTANCE:CookieDatabase?=null
        fun getInstance(context: Context):CookieDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CookieDatabase::class.java,
                        "cookie_data_database"
                    ).build()
                }
                return instance
            }

        }

    }

}