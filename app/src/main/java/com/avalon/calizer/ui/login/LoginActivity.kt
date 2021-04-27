package com.avalon.calizer.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.avalon.calizer.data.repository.launchActivity
import com.avalon.calizer.databinding.ActivityLoginBinding

import com.avalon.calizer.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val mCookiesVewModel: LoginViewModel by viewModels()


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
                   // PREFERENCES.allCookie = loginCookies
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




                }
            }
        }

    }


    private suspend fun LoginTest():Boolean{
        mCookiesVewModel.getReelsTray(allCookie)
        return true
    }

}