package com.avalon.calizer.di

import android.content.Context
import android.content.SharedPreferences
import androidx.activity.result.ActivityResultRegistry
import androidx.room.Room
import com.avalon.calizer.data.api.ApiHelper
import com.avalon.calizer.data.api.ApiHelperImpl
import com.avalon.calizer.data.api.ApiInterceptor
import com.avalon.calizer.data.api.ApiService
import com.avalon.calizer.data.local.MyDatabase
import com.avalon.calizer.ui.main.fragments.profile.photocmp.PhotoUploadObserver
import com.avalon.calizer.utils.Constants
import com.avalon.calizer.utils.Constants.USER_DATABASE
import com.avalon.calizer.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL


    @Provides
    @Singleton
    fun provideMyDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MyDatabase::class.java,
        USER_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideRoomDao(db: MyDatabase) = db.roomDao

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideOkHttpClient(apiInterceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(apiInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Singleton
    @Provides
    fun provideApiInterceptor(apiInterceptor: ApiInterceptor):Interceptor = apiInterceptor


   @Singleton
   @Provides
    fun provideGlide(
        @ApplicationContext app: Context
   ) = Glide.with(app).setDefaultRequestOptions(
       RequestOptions()
           .circleCrop()
   )


    @Provides
    @Singleton
    fun mySharedPreferences(@ApplicationContext app: Context): SharedPreferences {
        return app.getSharedPreferences(
            "my_prefs",
            Context.MODE_PRIVATE
        )
    }



}