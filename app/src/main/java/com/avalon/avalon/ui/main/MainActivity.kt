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
import com.avalon.avalon.preferences
import com.avalon.avalon.utils.Utils
import kotlinx.coroutines.*
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

        if(!preferences.allCookie.isNullOrEmpty()){
            cookies = preferences.allCookie!!
            getUserList(userId = "19748713375")
        }

        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {

                    if(size<4){
                        size++
                        getUserList(
                            maxId = response.body()?.nextMaxId,
                            userId = "19748713375"
                            )
                    }else{
                        val timeNow:Long = System.currentTimeMillis()
                        val date:Date = Date(System.currentTimeMillis())
                        Log.d("Response", "time now-> $timeNow")
                        Log.d("Response", "date-> $date")
                        Log.d("Response", "date.gettime-> ${date.time}")
                       // preferences.


                    }


                for (data in response.body()?.users!!){
                    Log.d("Response",""+data.username)
                }
                Log.d("Response","-----------------------")
            }
        })

    }


    fun getUserList(maxId: String? = null,userId:String){
       if(!maxId.isNullOrEmpty()){
           CoroutineScope(Dispatchers.IO).launch {
               viewModel.getUserFollowers(
                   userId = userId,
                   maxId = maxId,
                   rnkToken = Utils.generateUUID(),
                   cookies
               )
               delay((200+Random(250).nextInt().toLong()))
           }
       }else{
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getUserFollowers(
                    userId = userId,
                    cookies = cookies,
                    maxId = null,
                    rnkToken = null
                )
                delay((200+Random(250).nextInt().toLong()))
            }
        }

    }



}