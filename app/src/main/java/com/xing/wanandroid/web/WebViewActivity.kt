package com.xing.wanandroid.web


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.webkit.WebView
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.web.contract.WebContract
import com.xing.wanandroid.web.presenter.WebPresenter
import com.xing.wanandroid.widget.ProgressWebView

class WebViewActivity : BaseMVPActivity<WebContract.View, WebPresenter>() {

    private lateinit var toolbar: Toolbar
    private lateinit var webView: ProgressWebView

    companion object {
        val URL: String = "url"
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_web)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        webView = findViewById(R.id.pwv_webview)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_web_view
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun createPresenter(): WebPresenter {
        return WebPresenter()
    }

    override fun initData() {
        super.initData()
        val bundle: Bundle? = intent.extras
        val url = bundle?.getString(URL)
        webView.loadUrl(url)
        webView.setWebViewCallback(object : ProgressWebView.OnWebViewCallback {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolbar.title = title
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            }

            override fun onPageFinished(view: WebView, url: String) {
            }

            override fun onLoadResource(view: WebView, url: String) {
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            }

            override fun onPageLoadComplete() {
            }
        })

        presenter.addFavorite()
    }
}
