package com.avalon.calizer.di

import android.content.Context
import android.content.SharedPreferences
import android.webkit.WebView
import androidx.room.Room
import com.avalon.calizer.data.api.ApiHelper
import com.avalon.calizer.data.api.ApiHelperImpl
import com.avalon.calizer.data.api.ApiInterceptor
import com.avalon.calizer.data.api.ApiService
import com.avalon.calizer.data.local.MyDatabase
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.dialog.StoryBottomSheet
import com.avalon.calizer.utils.Constants
import com.avalon.calizer.utils.Constants.USER_DATABASE
import com.avalon.calizer.utils.Network
import com.avalon.calizer.utils.NetworkConnectivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
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
    fun provideFaceDetectorOptions():FaceDetectorOptions = FaceDetectorOptions.Builder()
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .build()

    @Singleton
    @Provides
    fun provideFaceDetector(options: FaceDetectorOptions):FaceDetector = FaceDetection.getClient(options)

    @Singleton
    @Provides
    fun providePoseDetectorOptions():PoseDetectorOptions = PoseDetectorOptions.Builder()
        .setDetectorMode(PoseDetectorOptions.SINGLE_IMAGE_MODE)
        .build()

    @Singleton
    @Provides
    fun providePoseDetector(options: PoseDetectorOptions):PoseDetector = PoseDetection.getClient(options)


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

    @Provides
    @Singleton
    fun provideNetwork(@ApplicationContext context: Context):NetworkConnectivity{
        return Network(context)
    }

    @Provides
    @Singleton
    fun provideBottomSheet():StoryBottomSheet = StoryBottomSheet()



}