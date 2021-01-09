package com.avalon.avalon.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.local.RoomDao
import com.avalon.avalon.data.local.RoomData
import com.avalon.avalon.data.local.MyDatabase
import com.avalon.avalon.data.repository.RoomRepository
import com.avalon.avalon.data.repository.Repository
import com.avalon.avalon.data.repository.launchActivity
import com.avalon.avalon.databinding.ActivityLoginBinding
import com.avalon.avalon.PREFERENCES
import com.avalon.avalon.ui.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mCookiesVewModel: LoginViewModel
    var csfr = ""
    var dsUserID = ""
    var igDId = ""
    var rur = ""
    var sessID = ""
    var shbid = ""
    var shbts = ""
    var mid = ""
    var allCookie = ""
    var lastControl: Boolean = true
    var getCookies: Boolean = false
    private lateinit var context: Context

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        val dao: RoomDao = MyDatabase.getInstance(application).roomDao
        val dbRepository = RoomRepository(dao)
        val repository = Repository()
        val factory = LoginViewModelFactory(dbRepository,repository)
        mCookiesVewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        mCookiesVewModel.cookies.observe(this, Observer { data->
            Log.d("Response", "data geldi bro")
        })

        mCookiesVewModel.reelsTray.observe(this, Observer { response ->
            if(response.isSuccessful){
               Log.d("Response",response.body()?.status.toString()!!)
                launchActivity<MainActivity> {  }
            }
        })

        val loginUrl: String = "https://www.instagram.com/accounts/login/"
        val webView: WebView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportZoom(true)
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        // val cookieManager:CookieManager
        CookieManager.getInstance().setAcceptCookie(true)
        webView.loadUrl(loginUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!url.equals(loginUrl) && lastControl) {
                    lastControl = false
                    val loginCookies = CookieManager.getInstance().getCookie(url)
                    Log.d("Response",loginCookies.toString())
                    PREFERENCES.allCookie = loginCookies
                    allCookie = loginCookies
                    for (data in loginCookies.split(";")) {
                        val trim2 = data.trim()
                        val datalast = trim2.split("=").toTypedArray()

                        when {
                            trim2.startsWith("sessionid") -> {
                                sessID = datalast[1]
                            }
                            trim2.startsWith("ds_user_id") -> {
                                dsUserID = datalast[1]
                            }
                            trim2.startsWith("ig_did") -> {
                                igDId = datalast[1]
                            }
                            trim2.startsWith("mid") -> {
                                mid = datalast[1]
                            }
                            trim2.startsWith("csrftoken") -> {
                                csfr = datalast[1]
                            }
                            trim2.startsWith("shbid") -> {
                                shbid = datalast[1]
                            }
                            trim2.startsWith("shbts") -> {
                                shbts = datalast[1]
                            }
                            trim2.startsWith("rur") -> {
                                rur = datalast[1]
                            }
                        }
                    }
                    val cookiesData = RoomData(
                        cookieId = 1,
                        csfr = csfr,
                        dsUserID = dsUserID,
                        mid = mid,
                        rur = rur,
                        sessID = sessID,
                        shbid = shbid,
                        shbts = shbts,
                        allCookie = loginCookies
                    )
                    Log.d("Response","allCookie->"+ PREFERENCES.allCookie)
                    GlobalScope.launch {
                      val success1 =  insertCookiesToDatabase(cookiesData)
                      val success2 = LoginTest()

                      if(success1&&success2){
                          Log.d("Response","BASARILI")
                      }

                    }


                }
            }
        }

    }

    private fun insertCookiesToDatabase(roomData: RoomData):Boolean {
        mCookiesVewModel.addCookie(roomData)
        getCookies = true
        return true
    }
    private suspend fun LoginTest():Boolean{
        mCookiesVewModel.getReelsTray(allCookie)
        return true
    }

}