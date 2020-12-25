package com.avalon.avalon.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.local.CookieDao
import com.avalon.avalon.data.local.CookieData
import com.avalon.avalon.data.local.CookieDatabase
import com.avalon.avalon.data.remote.insresponse.ApiResponseUserFollowers
import com.avalon.avalon.data.remote.insresponse.User
import com.avalon.avalon.data.repository.CookieRepository
import com.avalon.avalon.data.repository.Repository
import com.avalon.avalon.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import okhttp3.internal.wait
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var cookies: String
    private var size:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao: CookieDao = CookieDatabase.getInstance(application).cookieDao
        val dbRepository = CookieRepository(dao)
        val repository = Repository()
        val factory = MainViewModelFactory(dbRepository, repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        Log.d("Response", "" + Thread.currentThread().name)
        viewModel.getCookies()

        viewModel.cookies.observe(this, { response ->
            cookies = response.allCookie
            viewModel.getUserFollowers(
                "https://i.instagram.com/api/v1/friendships/19748713375/followers/",
                cookies
            )
        })


        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {

                if (!response.body()?.nextMaxId.isNullOrEmpty()) {
                    if(size<5){
                        size++

                        getUserList(response.body()?.nextMaxId!!)
                    }

                }
                for (data in response.body()?.users!!){
                    Log.d("Response",""+data.username)
                }
                Log.d("Response","-----------------------")
            }
        })

    }


    fun getUserList(maxId:String){
        val uuid: String = UUID.randomUUID().toString().replace("-", "");
        Log.d("Response", uuid)
        val url:String =
            "https://i.instagram.com/api/v1/friendships/19748713375/followers/?max_id=$maxId&rank_token=19748713375_$uuid"
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUserFollowers(
                url,
                cookies
            )

            delay((200+Random(250).nextInt().toLong()))
        }
    }



}