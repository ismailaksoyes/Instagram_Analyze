package com.avalon.avalon.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.local.CookieDao
import com.avalon.avalon.data.local.CookieDatabase
import com.avalon.avalon.data.repository.CookieRepository
import com.avalon.avalon.data.repository.Repository
import com.avalon.avalon.databinding.ActivityMainBinding
import com.avalon.avalon.PREFERENCES
import com.avalon.avalon.data.local.FollowersData
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.avalon.utils.Utils
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var cookies: String
    private var size: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao: CookieDao = CookieDatabase.getInstance(application).cookieDao
        val dbRepository = CookieRepository(dao)
        val repository = Repository()
        val factory = MainViewModelFactory(dbRepository, repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        if (!PREFERENCES.allCookie.isNullOrEmpty()) {
            cookies = PREFERENCES.allCookie!!
            // getUserList(userId = "19748713375")
            if (getTimeStatus(PREFERENCES.followersUpdateDate)) {
                getUserList(userId = "19748713375")
                PREFERENCES.followersUpdateDate = System.currentTimeMillis()
            }
        }

        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {

                if (!response.body()?.nextMaxId.isNullOrEmpty() && size < 7) {
                    size++
                    getUserList(
                        maxId = response.body()?.nextMaxId,
                        userId = "19748713375"
                    )
                } else {
                    Log.d("Response", "null max id")
                }
                setRoomFollowers(response.body())

                Log.d("Response", "-----------------------")
            }
        })

    }
    private fun setRoomFollowers(followersData: ApiResponseUserFollowers?){
        CoroutineScope(Dispatchers.Default).launch {
            if (followersData != null) {
                val newList = FollowersData()
                for(data in followersData.users){
                    newList.pk= data.pk
                    newList.fullName = data.fullName
                    newList.profilePicUrl = data.profilePicUrl
                    newList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                    newList.isPrivate=data.isPrivate
                    newList.isVerified = data.isPrivate
                    newList.username = data.username
                    viewModel.addFollowers(newList)
                    Log.d("Response","${data.pk} ${data.fullName}")
                }

            }


        }.start()

    }


    private fun getTimeStatus(date: Long): Boolean {
        val timeDif = Utils.getTimeDifference(Date(date))
        // return timeDif > 1
        return true
    }

    fun getUserList(maxId: String? = null, userId: String) {
        if (!maxId.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getUserFollowers(
                    userId = userId,
                    maxId = maxId,
                    rnkToken = Utils.generateUUID(),
                    cookies
                )
                delay((200 + Random(250).nextInt().toLong()))
            }.start()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getUserFollowers(
                    userId = userId,
                    cookies = cookies,
                    maxId = null,
                    rnkToken = null
                )
                delay((200 + Random(250).nextInt().toLong()))
            }.start()
        }

    }


}

