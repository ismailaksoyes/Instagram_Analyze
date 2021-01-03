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
    private val arrList = ArrayList<FollowersData>()
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
                    viewModel.addFollowers(arrList)
                }
                setRoomFollowers(response.body())

               // Log.d("Response", "-----------------------")
            }
        })

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
                arrList.add(newList)

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

