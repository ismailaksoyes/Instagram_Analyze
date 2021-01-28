
package com.avalon.calizer.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.avalon.calizer.R
import com.avalon.calizer.data.repository.Repository
import com.avalon.calizer.ui.main.MainActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    lateinit var button:Button
    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val repository = Repository()
        val factory = SplashViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SplashViewModel::class.java)
        button = findViewById(R.id.button1)
        button.setOnClickListener {
            val ttt = Intent(this,MainActivity::class.java)
            startActivity(ttt)
            finish()
        }
        //test()
        Log.d("Response","main ->${Thread.currentThread()} has run.")


           // test()



    }


    fun test():Job{

        return CoroutineScope(Dispatchers.IO).launch {
            Log.d("Response","Job ->${Thread.currentThread()} has run.")
            delay(10000)
            Log.d("Response","Test1")
            delay(10000)
            Log.d("Response","Test2")
            delay(10000)
            write()
            Log.d("Response","Test3")
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("Response","Job ->${Thread.currentThread()} has run.")
                Log.d("Response","TestScope")
            }

        }
    }

    fun write(){
        Log.d("Response","WJob ->${Thread.currentThread()} has run.")
        Log.d("Response","Test....")
    }
}