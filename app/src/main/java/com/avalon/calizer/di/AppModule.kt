package com.avalon.calizer.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import com.avalon.calizer.data.local.MyDatabase
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.utils.Constants.USER_DATABASE
import com.avalon.calizer.utils.MySharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyDatabase(
       @ApplicationContext app:Context
    ) = Room.databaseBuilder(
        app,
        MyDatabase::class.java,
        USER_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideRoomDao(db:MyDatabase) = db.roomDao

    @Provides
    @Singleton
    fun mySharedPreferences(@ApplicationContext app: Context):SharedPreferences{
        return app.getSharedPreferences(
            "my_prefs",
            Context.MODE_PRIVATE
        )
    }


}