package com.avalon.calizer.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun mySharedPreferences(@ApplicationContext context: Context):SharedPreferences{
        return context.getSharedPreferences(
            "my_prefs",
            Context.MODE_PRIVATE
        )
    }
}