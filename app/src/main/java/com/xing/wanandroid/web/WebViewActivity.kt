package com.xing.wanandroid.web


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import com.xing.wanandroid.base.mvp.BaseMVPActivity
import com.xing.wanandroid.utils.*
import com.xing.wanandroid.web.bean.WebOptBean
import com.xing.wanandroid.web.contract.WebContract
import com.xing.wanandroid.web.presenter.WebPresenter
import com.xing.wanandroid.widget.XWebView
import com.xing.wanandroid.widget.WebDialogFragment
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.constants.ConstantsAPI
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.xing.wanandroid.R
import com.xing.wanandroid.web.bean.AddFavoriteResponse


class WebViewActivity : BaseMVPActivity<WebContract.View, WebPresenter>(), WebContract.View {

    private lateinit var toolbar: Toolbar
    private var webView: XWebView? = null
    private var moreMenuItem: MenuItem? = null
    private var title: String? = null
    private var link: String? = null
    private var id: Int? = -1
    private var author: String? = null
    private var dialogFragment: WebDialogFragment? = null
    private var loadUrl: String? = null
    private var favoriteSuccessView: View? = null
    private var appId: String = "wx2c753629bd2e94bd"

    companion object {
        val URL: String = "url"
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_web)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar?.elevation = dp2px(mContext, 5f)
        toolbar.setNavigationOnClickListener {
            goBack()
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
        initWxShare()
        val bundle: Bundle? = intent.extras
        loadUrl = bundle?.getString(URL)
        id = bundle?.getInt(ID)
        link = bundle?.getString(LINK)
        title = bundle?.getString(TITLE)
        author = bundle?.getString(AUTHOR)
        webView?.loadUrl(loadUrl)
        webView?.setWebViewCallback(object : XWebView.OnWebViewCallback {
            override fun onPageFinished(view: WebView?, url: String?) {
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Log.e("debug", "progres = $newProgress")
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolbar.title = title
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            }

            override fun onLoadResource(view: WebView, url: String) {
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            }

            override fun onPageLoadComplete() {
                moreMenuItem?.isVisible = true
            }
        })
    }

    private fun initWxShare() {
        val wxapi = WXAPIFactory.createWXAPI(mContext, "", true)
        // 将应用的appId注册到微信
        wxapi.registerApp(appId)
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // 将该app注册到微信
                wxapi.registerApp(appId)
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
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
        moreMenuItem = menu?.findItem(R.id.item_more)
        // 默认是不显示的,页面加载完成才显示
        moreMenuItem?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * 菜单项点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.item_more) {
            showMoreDialog()
        }
        return true
    }

    private fun showMoreDialog() {
        val dataList = ArrayList<WebOptBean>()
        dataList.add(WebOptBean(R.drawable.ic_favorite_white, "收藏"))
        dataList.add(WebOptBean(R.drawable.ic_share, "朋友圈"))
        dataList.add(WebOptBean(R.drawable.ic_wx_friend, "微信好友"))
        dataList.add(WebOptBean(R.drawable.ic_link, "复制链接"))
        dataList.add(WebOptBean(R.drawable.ic_refresh, "刷新"))
        dataList.add(WebOptBean(R.drawable.ic_browser, "浏览器打开"))
        if (dialogFragment == null) {
            dialogFragment = WebDialogFragment.newInstance(dataList)
        }
        dialogFragment?.setOnItemClickListener(object : WebDialogFragment.OnItemClickListener {
            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> addArticleFavorite()
                    1 -> shareToWeChat(SendMessageToWX.Req.WXSceneTimeline)
                    2 -> shareToWeChat(SendMessageToWX.Req.WXSceneSession)
                    3 -> copyLink()
                    4 -> refreshPage()
                    5 -> openByBrowser()
                }
            }
        })
        val dialog = dialogFragment?.dialog
        val isShowing = dialog?.isShowing ?: false
        if (isShowing) {
            return
        }
        dialogFragment?.show(supportFragmentManager, WebDialogFragment().javaClass.name)
    }

    /**
     * 返回
     */
    private fun goBack() {
        val canGoBack = webView?.canGoBack() ?: false
        if (canGoBack) {
            webView?.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView?.removeAllViews()
        webView?.clearHistory()
        webView = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goBack()
    }


    /**
     * 刷新
     */
    private fun refreshPage() {
        webView?.reload()
    }

    /**
     * 拷贝链接
     */
    private fun copyLink() {
        // 获取剪贴板管理器：
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
//        val clipData = ClipData.newPlainText("Label", "baidu")
        // 创建链接型 clipData
        val clipData = ClipData.newRawUri("Label", Uri.parse(loadUrl))
        // 将ClipData内容放到系统剪贴板里。
        cm.primaryClip = clipData
        Toast.makeText(mContext, "已复制至剪切板", Toast.LENGTH_LONG).show()
    }

    /**
     * 分享至微信
     */
    private fun shareToWeChat(scene: Int) {
        WeChatShareUtils.shareWeb(mContext, "", loadUrl ?: "", "", null, null, scene)
    }

    /**
     * 文章收藏
     */
    private fun addArticleFavorite() {
        presenter.addFavorite(id ?: -1, title ?: "", author ?: "", link ?: "")
    }

    /**
     * 浏览器打开
     */
    private fun openByBrowser() {
        val uri = Uri.parse(loadUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }


    /**
     * 收藏成功回调
     */
    override fun onAddFavorited(addFavoriteResponse: AddFavoriteResponse?) {
        if (favoriteSuccessView == null) {
            favoriteSuccessView = LayoutInflater.from(mContext).inflate(R.layout.layout_favorite_toast, null, false)
            ToastUtils.show(mContext, favoriteSuccessView!!)
        }
    }

}
