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
import com.avalon.avalon.data.repository.CookieRepository
import com.avalon.avalon.data.repository.Repository
import com.avalon.avalon.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import okhttp3.internal.wait

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var cookies: String
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

        viewModel.cookies.observe(this, Observer { response ->
            cookies = response.allCookie
            viewModel.getUserFollowers(19748713375, cookies)
        })

        viewModel.allFollowers.observe(this, Observer { response ->
            if (response.isSuccessful) {
                for (data in response.body()?.users!!) {
                    Log.d("Response", data.username)
                }
            }
        })

    }



}