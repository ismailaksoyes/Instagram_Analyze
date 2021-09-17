package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

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
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentStoryShowBinding
import com.avalon.calizer.ui.tutorial.TutorialFragment


class StoryShowFragment : Fragment() {

    lateinit var binding: FragmentStoryShowBinding

    lateinit var webView: WebView

    companion object {
        private const val ARG_URL = "ARG_URL"

        fun getInstance(url: String) = StoryShowFragment().apply {
            arguments = bundleOf(ARG_URL to url)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryShowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        val url = requireArguments().getString(ARG_URL)
        Log.d("getUrlStory","$url")
        url?.let { itUrl->
            webView.loadUrl(itUrl)
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("getUrlStoryFin",url.toString())
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.d("getUrlStoryRe",url.toString())
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
        webView.settings.loadsImagesAutomatically = true
    }







}