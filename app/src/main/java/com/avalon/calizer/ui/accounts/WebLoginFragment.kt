package com.avalon.calizer.ui.accounts

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.databinding.FragmentWebLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WebLoginFragment : Fragment() {
   private lateinit var binding: FragmentWebLoginBinding
   @Inject
    lateinit var viewModel: AccountsViewModel


    var csfr = ""
    var dsUserID = 0L
    var igDId = ""
    var rur = ""
    var sessID = ""
    var shbid = ""
    var shbts = ""
    var mid = ""
    var allCookie = ""
    var lastControl: Boolean = true
    var getCookies: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        viewModel.allAccounts.observe(viewLifecycleOwner, Observer { data->
//            Log.d("Response", "data geldi bro")
//        })

        viewModel.reelsTray.observe(viewLifecycleOwner, Observer {
            Log.d("Response", "${it.data}")
        })
        viewModel.userDetails.observe(viewLifecycleOwner, Observer {
            Log.d("Response", "${it.data?.user?.profilePicUrl}")
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
                                dsUserID = datalast[1].toLong()
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

                    val accountData = AccountsData(
                        csfr = csfr,
                        dsUserID = dsUserID,
                        mid = mid,
                        rur = rur,
                        sessID = sessID,
                        shbid =  shbid,
                        shbts = shbts,
                        allCookie = allCookie
                    )
                    // Log.d("Response","allCookie->"+ PREFERENCES.allCookie)
                    GlobalScope.launch {
                        val success1 =  insertCookiesToDatabase(accountData)
                        val success2 = LoginTest()

                        if(success1&&success2){
                            Log.d("Response","BASARILI")
                        }

                    }


                }
            }
        }
    }
    private fun insertCookiesToDatabase(accountsData: AccountsData):Boolean {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.addAccount(accountsData)
            getCookies = true
        }

        return true
    }
    private suspend fun LoginTest():Boolean{
       // mCookiesVewModel.getReelsTray(allCookie)
       // viewModel.getReelsTray(allCookie)
       // viewModel.getUserDetails(allCookie,dsUserID)
        return true
    }


}