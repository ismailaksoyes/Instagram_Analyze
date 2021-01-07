package com.avalon.avalon.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.local.RoomDao
import com.avalon.avalon.data.local.MyDatabase
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
        val dao: RoomDao = MyDatabase.getInstance(application).roomDao
        val dbRepository = RoomRepository(dao)
        val repository = Repository()
        val factory = MainViewModelFactory(dbRepository, repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        PREFERENCES.firstLogin = true
        if (!PREFERENCES.allCookie.isNullOrEmpty()) {
            cookies = PREFERENCES.allCookie!!
            if (Utils.getTimeStatus(PREFERENCES.followersUpdateDate)) {

                getUserList(userId = "19748713375")

                PREFERENCES.followersUpdateDate = System.currentTimeMillis()
            }
        }

        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {

                if (!response.body()?.nextMaxId.isNullOrEmpty()) {


                    getUserList(
                        maxId = response.body()?.nextMaxId,
                        userId = "19748713375"
                    )


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
        if (PREFERENCES.firstLogin) {

            for (data in followersLastList) {
                val newList = FollowersData()
                newList.pk = data.pk
                newList.fullName = data.fullName
                newList.profilePicUrl = data.profilePicUrl
                newList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                newList.isPrivate = data.isPrivate
                newList.isVerified = data.isPrivate
                newList.username = data.username
                followersList.add(newList)

            }
            Log.d("Response", "list-> " + followersList.size.toString())
            Log.d("Response", "listlast-> " + followersLastList.size.toString())
            viewModel.addLastFollowers(followersLastList)
            viewModel.addFollowers(followersList)

            PREFERENCES.firstLogin = false

        } else {


            viewModel.addLastFollowers(followersLastList)

        }
    }

    private fun setRoomFollowers(followersData: ApiResponseUserFollowers?) {

        if (followersData != null) {

            for (data in followersData.users) {
                val newList = LastFollowersData()
                newList.pk = data.pk
                newList.fullName = data.fullName
                newList.profilePicUrl = data.profilePicUrl
                newList.hasAnonymousProfilePicture = data.hasAnonymousProfilePicture
                newList.isPrivate = data.isPrivate
                newList.isVerified = data.isPrivate
                newList.username = data.username
                followersLastList.add(newList)

                //  Log.d("Response", "${data.pk} ${data.fullName} ${data.username}")
            }


        }

    }

    fun getUserList(maxId: String? = null, userId: String): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            delay((500 + (0..250).random()).toLong())
            Log.d("Response", "${(20000 + Random().nextInt(250).toLong())}")
            if (!maxId.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("Response", "testtt")
                    viewModel.getUserFollowers(
                        userId = userId,
                        maxId = maxId,
                        rnkToken = Utils.generateUUID(),
                        cookies
                    )


                }

            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    //Thread.sleep((200 + Random(250).nextInt().toLong()))

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


}


