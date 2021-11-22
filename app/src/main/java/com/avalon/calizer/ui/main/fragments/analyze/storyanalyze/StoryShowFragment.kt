package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentStoryShowBinding
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.ui.tutorial.TutorialFragment
import dagger.hilt.android.lifecycle.HiltViewModel


class StoryShowFragment : BaseFragment<FragmentStoryShowBinding>(FragmentStoryShowBinding::inflate) {


    private lateinit var webView: WebView

    companion object {
        private const val ARG_URL = "ARG_URL"

        fun getInstance(url: String) = StoryShowFragment().apply {
            arguments = bundleOf(ARG_URL to url)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        val url = requireArguments().getString(ARG_URL)
        openWebUrl(url)
        closeStoryOb()
    }

    private fun openWebUrl(url: String?){
        url?.let { itUrl->
            Log.d("TESTURL",itUrl)
            webView.loadUrl(itUrl)
        }
    }

    private fun closeStoryOb(){
        binding.ivCloseStory.setOnClickListener {
            val action = StoryViewsFragmentDirections.actionStoryViewsFragmentToStoryFragment()
            findNavController().navigate(action)
        }
    }


    private fun setupWebView() {
        webView = binding.webView
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = false
        webView.settings.setSupportZoom(true)
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.settings.loadsImagesAutomatically = true
    }







}