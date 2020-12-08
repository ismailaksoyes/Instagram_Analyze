package com.avalon.avalon.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.data.local.CookieDao
import com.avalon.avalon.data.local.CookieData
import com.avalon.avalon.data.local.CookieDatabase
import com.avalon.avalon.data.repository.CookieRepository
import com.avalon.avalon.databinding.ActivityLoginBinding

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
    var lastControl: Boolean = true
    var getCookies: Boolean = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao: CookieDao = CookieDatabase.getInstance(application).cookieDao
        val repository = CookieRepository(dao)
        val factory = LoginViewModelFactory(repository)
        mCookiesVewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        if (getCookies) {
            mCookiesVewModel.allCookies.observe(this, Observer { data ->
                Log.d("Response", "data geldi bro")
            })
        }
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
                    val cookiesData = CookieData(
                        cookieId = 1,
                        csfr = csfr,
                        dsUserID = dsUserID,
                        mid = mid,
                        rur = rur,
                        sessID = sessID,
                        shbid = shbid,
                        shbts = shbts
                    )
                    insertCookiesToDatabase(cookiesData)

                }
            }
        }

    }

    private fun insertCookiesToDatabase(cookieData: CookieData) {
        mCookiesVewModel.addCookie(cookieData)
        Log.d("Response", "cokiyi bastım")
        getCookies = true
    }

}