package com.xing.wanandroid.widget

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.xing.wanandroid.utils.dp2px

/**
 * 封装 WebView， https://developer.android.google.cn/reference/android/webkit/WebView.html
 */
class XWebView : WebView {

    private lateinit var webProgressBar: WebProgressBar
    private var onWebViewCallback: OnWebViewCallback? = null
    private var isProgressBarAdded: Boolean = true    // 当前 ProgressBar 是否已经添加
    private var progressHandler: Handler = Handler(Looper.getMainLooper())
    private var isStarted = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initWebView(context, attributeSet)
        initProgressBar()
        webViewClient = XWebViewClient()
        webChromeClient = XWebViewChromeClient()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(context: Context, attributeSet: AttributeSet) {
        val settings = settings
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
        webProgressBar.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dp2px(context, 6f).toInt())
        addView(webProgressBar)

    }

    private var runnable = Runnable {
        @Override
        fun run() {

        }
    }

    /**
     * 设置 ProgressBar 是否可见
     */
    fun setProgressBarVisible(visible: Boolean) {
        if (visible) {
            // 没有添加，则执行添加
            if (!isProgressBarAdded) {
                addView(webProgressBar)
            }
        } else {   // 设置不显示 ProgressBar, 如果当前已经添加，则执行移除
            if (isProgressBarAdded) {
                removeView(webProgressBar)
            }
        }
        isProgressBarAdded = visible
    }


    inner class XWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            onWebViewCallback?.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            onWebViewCallback?.onPageFinished(view, url)
        }

    }

    inner class XWebViewChromeClient : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            onWebViewCallback?.onReceivedTitle(view, title)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (isProgressBarAdded) {
                val url: String = view?.url ?: ""
                webProgressBar.visibility = View.VISIBLE
                val curProgress = webProgressBar.getProgress()
                if (!TextUtils.isEmpty(url)) {
                    if (url.startsWith("file")) {
                        startProgressAnimation(newProgress, curProgress)
                        if (newProgress == 100) {
                            progressHandler.postDelayed(object : Runnable {
                                override fun run() {
                                    webProgressBar.visibility = View.GONE
                                }
                            }, 100)
                        }
                    } else {   // 加载网络 html ，进度是反复增加的
                        if (newProgress >= 100 && !isStarted) {
                            isStarted = true
                        } else {
                            if (newProgress < curProgress) {
                                return
                            }
                            startProgressAnimation(newProgress, curProgress)
                            if (newProgress == 100) {
                                // 判断页面是否加载完成，不使用 onPageFinished
                                onWebViewCallback?.onPageLoadComplete()
                                progressHandler.postDelayed(object : Runnable {
                                    override fun run() {
                                        webProgressBar.visibility = View.GONE
                                    }
                                }, 100)
                            }

                        }
                    }
                }
            }
            onWebViewCallback?.onProgressChanged(view, newProgress)
        }

    }

    private fun startProgressAnimation(newProgress: Int, curProgress: Int) {
        val animator = ObjectAnimator.ofInt(webProgressBar, "progress", curProgress, newProgress)
        animator.duration = 300
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    fun setWebViewCallback(onWebViewCallback: OnWebViewCallback) {
        this.onWebViewCallback = onWebViewCallback
    }

    interface OnWebViewCallback {
        fun onProgressChanged(view: WebView?, newProgress: Int)

        fun onReceivedTitle(view: WebView?, title: String?)

        fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)

        fun onPageFinished(view: WebView?, url: String?)

        fun onLoadResource(view: WebView, url: String)

        fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String)

        /**
         * 页面加载完成,不使用 onPageFinish() 因为 onPageFinish() 会被回调两次
         */
        fun onPageLoadComplete()
    }
}