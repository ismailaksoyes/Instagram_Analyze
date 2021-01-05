package com.avalon.avalon.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.local.RoomDao
import com.avalon.avalon.data.local.RoomDatabase
import com.avalon.avalon.data.repository.RoomRepository
import com.avalon.avalon.data.repository.Repository
import com.avalon.avalon.databinding.ActivityMainBinding
import com.avalon.avalon.PREFERENCES
import com.avalon.avalon.data.local.FollowersData
import com.avalon.avalon.data.local.LastFollowersData
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.avalon.utils.Utils
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var cookies: String
    private val followersList = ArrayList<FollowersData>()
    private val followersLastList = ArrayList<LastFollowersData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao: RoomDao = RoomDatabase.getInstance(application).roomDao
        val dbRepository = RoomRepository(dao)
        val repository = Repository()
        val factory = MainViewModelFactory(dbRepository, repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        followersData()
        if (!PREFERENCES.allCookie.isNullOrEmpty()) {
            cookies = PREFERENCES.allCookie!!
            if (Utils.getTimeStatus(PREFERENCES.followersUpdateDate)) {

                CoroutineScope(Dispatchers.IO).launch {
                    getUserList(userId = "19748713375")
                }
                PREFERENCES.followersUpdateDate = System.currentTimeMillis()
            }
        }

        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {

                if (!response.body()?.nextMaxId.isNullOrEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        getUserList(
                            maxId = response.body()?.nextMaxId,
                            userId = "19748713375"
                        )
                    }

                } else {
                   // Log.d("Response", "null max id")

                    followersData()
                }
                setRoomFollowers(response.body())

               // Log.d("Response", "-----------------------")
            }
        })

    }

    private fun followersData() {
        if(PREFERENCES.firstLogin){
            viewModel.addFollowers(followersList)
            followersLastList.add(followersList)

            viewModel.addLastFollowers(followersList)

        }
    }

    private fun setRoomFollowers(followersData: ApiResponseUserFollowers?) {

        if (followersData != null) {

            for (data in followersData.users) {
                val newList = FollowersData()
                newList.pk = data.pk
                newList.fullName = data.fullName
                newList.profilePicUrl = data.profilePicUrl
                newList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                newList.isPrivate = data.isPrivate
                newList.isVerified = data.isPrivate
                newList.username = data.username
                followersList.add(newList)

              //  Log.d("Response", "${data.pk} ${data.fullName} ${data.username}")
            }


        }

    }

    suspend fun getUserList(maxId: String? = null, userId: String) {
        if (!maxId.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("Delay", "delay1")
                delay((20000 + Random(250).nextInt().toLong()))
                // Thread.sleep((200 + Random(250).nextInt().toLong()))
                Log.d("Delay", "delay2")

                viewModel.getUserFollowers(
                    userId = userId,
                    maxId = maxId,
                    rnkToken = Utils.generateUUID(),
                    cookies
                )


            }

        } else {
            CoroutineScope(Dispatchers.IO).launch {
                // Thread.sleep((200 + Random(250).nextInt().toLong()))
                delay((20000 + Random(250).nextInt().toLong()))
                viewModel.getUserFollowers(
                    userId = userId,
                    cookies = cookies,
                    maxId = null,
                    rnkToken = null
                )

            }

        }

    }


}

