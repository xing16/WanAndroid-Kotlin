package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xing.wanandroid.utils.dp2px

class ProgressWebView : WebView {

    private lateinit var webProgressBar: WebProgressBar
    private lateinit var onWebViewCallback: OnWebViewCallback

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        initWebSettings()
        initProgressBar()
        webViewClient = MyWebViewClient()
        webChromeClient = MyWebViewChromeClient()
    }


    private fun initWebSettings() {
        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        settings.displayZoomControls = false
        settings.builtInZoomControls = true
        settings.allowFileAccess = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.setAppCacheEnabled(true)
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.loadsImagesAutomatically = true
        settings.blockNetworkImage = false
        settings.blockNetworkLoads = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

    }

    private fun initProgressBar() {
        webProgressBar = WebProgressBar(context)
        webProgressBar.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 6f).toInt())
        addView(webProgressBar)
    }


    inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            onWebViewCallback.onPageStarted(view, url, favicon)
        }

    }

    inner class MyWebViewChromeClient : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            onWebViewCallback.onReceivedTitle(view, title)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            webProgressBar.setProgress(newProgress)
            onWebViewCallback.onProgressChanged(view, newProgress)
        }
    }

    fun setWebViewCallback(onWebViewCallback: OnWebViewCallback) {
        this.onWebViewCallback = onWebViewCallback
    }

    interface OnWebViewCallback {
        fun onProgressChanged(view: WebView?, newProgress: Int)

        fun onReceivedTitle(view: WebView?, title: String?)

        fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)

        fun onPageFinished(view: WebView, url: String)

        fun onLoadResource(view: WebView, url: String)

        fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String)

        /**
         * 页面加载完成,不使用 onPageFinish() 因为 onPageFinish() 会被回调两次
         */
        fun onPageLoadComplete()

    }


}