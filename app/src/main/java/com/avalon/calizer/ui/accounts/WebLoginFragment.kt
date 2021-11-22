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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.data.local.AccountsData
import com.avalon.calizer.data.local.weblogin.CookiesData
import com.avalon.calizer.databinding.FragmentWebLoginBinding
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.utils.CheckInternetConnection
import com.avalon.calizer.utils.Constants
import com.avalon.calizer.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WebLoginFragment : BaseFragment<FragmentWebLoginBinding>(FragmentWebLoginBinding::inflate) {

    val viewModel: AccountsViewModel by viewModels()

    lateinit var webView: WebView

    var lastControl: Boolean = true


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().setAcceptCookie(true)
        setupWebView()
        getCookieSplit()
        addCookieDatabase()
        cookieValid()
        getCookie()
        loginSuccess()
        initData()

    }

    private fun initData(){
        binding.ivBackBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_webLoginFragment_to_destination_accounts)
        }
    }

    private fun loginSuccess() {
        lifecycleScope.launchWhenCreated {
            viewModel.cookieData.collectLatest { itSuccess ->
                if (itSuccess is AccountsViewModel.AccountCookieState.Success) {
                    findNavController().navigate(R.id.action_webLoginFragment_to_destination_accounts)
                }
            }
        }

    }

    private fun cookieValid() {
        lifecycleScope.launchWhenCreated {
            viewModel.cookieData.collectLatest { itValid ->
                if (itValid is AccountsViewModel.AccountCookieState.CookieValid) {
                    viewModel.setCookies(itValid.cookies)

                }
            }
        }
    }

    private fun setupWebView() {
        webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportZoom(true)
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
    }

    private fun getCookieSplit() {
        lifecycleScope.launchWhenCreated {
            viewModel.cookieData.collect { itCookieData ->
                if (itCookieData is AccountsViewModel.AccountCookieState.Cookies) {
                    val cookiesData = CookiesData()
                    for (data in itCookieData.cookies.split(";")) {
                        val trim2 = data.trim()
                        val datalast = trim2.split("=").toTypedArray()
                        cookiesData.allCookie = itCookieData.cookies
                        when {
                            trim2.startsWith("sessionid") -> {
                                cookiesData.sessID = datalast[1]
                            }
                            trim2.startsWith("ds_user_id") -> {
                                cookiesData.dsUserID = datalast[1].toLong()
                            }
                            trim2.startsWith("ig_did") -> {
                                cookiesData.igDId = datalast[1]
                            }
                            trim2.startsWith("mid") -> {
                                cookiesData.mid = datalast[1]
                            }
                            trim2.startsWith("csrftoken") -> {
                                cookiesData.csfr = datalast[1]
                            }
                            trim2.startsWith("shbid") -> {
                                cookiesData.shbid = datalast[1]
                            }
                            trim2.startsWith("shbts") -> {
                                cookiesData.shbts = datalast[1]
                            }
                            trim2.startsWith("rur") -> {
                                cookiesData.rur = datalast[1]
                            }
                        }
                    }
                    viewModel.setSplitCookies(cookiesData)

                }

            }
        }
    }

    private fun addCookieDatabase() {
        lifecycleScope.launchWhenCreated {
            viewModel.cookieData.collect { itSplitData ->
                if (itSplitData is AccountsViewModel.AccountCookieState.SplitCookie) {
                    itSplitData.cookiesData.apply {
                        val accountData = AccountsData(
                            csfr = csfr,
                            dsUserID = dsUserID,
                            mid = mid,
                            rur = rur,
                            sessID = sessID,
                            shbid = shbid,
                            shbts = shbts,
                            allCookie = allCookie
                        )

                        viewModel.addAccount(accountData)
                    }

                }
            }
        }
    }

    private fun getCookie() {

        val checkInternetConnection = CheckInternetConnection()

        if (checkInternetConnection.isAvailableInternet(requireContext())) {
            webView.loadUrl("https://www.instagram.com/accounts/login/")
        } else {
            binding.clTopLogin.showSnackBar(
                "checkInternetConnection",
                Snackbar.LENGTH_INDEFINITE,
                "TRY AGAIN"
            ) {
                getCookie()
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!url.equals("https://www.instagram.com/accounts/login/") && lastControl) {
                    lastControl = false
                    val loginCookies = CookieManager.getInstance().getCookie(url)
                    lifecycleScope.launchWhenCreated {
                        viewModel.getReelsTray(loginCookies)
                    }
                }
            }
        }
    }


}