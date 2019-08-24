package com.xing.wanandroid.web


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import com.xing.wanandroid.R
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.utils.*
import com.xing.wanandroid.web.contract.WebContract
import com.xing.wanandroid.web.presenter.WebPresenter
import com.xing.wanandroid.widget.ProgressWebView

class WebViewActivity : BaseMVPActivity<WebContract.View, WebPresenter>() {

    private lateinit var toolbar: Toolbar
    private lateinit var webView: ProgressWebView
    private var favoriteMenuItem: MenuItem? = null
    private var title: String? = null
    private var link: String? = null
    private var id: Int? = -1
    private var author: String? = null

    companion object {
        val URL: String = "url"
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_web)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = dp2px(mContext, 5f).toFloat()
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
        id = bundle?.getInt(ID)
        link = bundle?.getString(LINK)
        title = bundle?.getString(TITLE)
        author = bundle?.getString(AUTHOR)
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


    }

    /**
     * 创建菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 获取菜单项
     */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        favoriteMenuItem = menu?.findItem(R.id.item_favorite)
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * 菜单项点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.item_favorite) {
            presenter.addFavorite(id ?: -1, title ?: "", author ?: "", link ?: "")
        }
        return true
    }

}
