package com.xing.wanandroid.setting

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.view.textclassifier.TextLinks
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xing.wanandroid.R
import com.xing.wanandroid.base.BaseActivity
import com.xing.wanandroid.setting.adapter.AboutLibraryAdapter
import com.xing.wanandroid.utils.dp2px
import com.xing.wanandroid.utils.gotoActivity
import com.xing.wanandroid.web.WebViewActivity
import java.lang.StringBuilder
import java.util.*

class AboutActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_about
    }

    override fun initView() {
        val toolbar: Toolbar = findViewById(R.id.tb_about)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "关于"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = dp2px(this, 5f)
        toolbar.setNavigationOnClickListener { finish() }
        val appDescTextView: TextView = findViewById(R.id.tv_app_desc)
        val recyclerView: RecyclerView = findViewById(R.id.rv_library)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val stringArray = resources.getStringArray(R.array.library_list)
        val arrayList = stringArray.toList() as ArrayList<String>
        recyclerView.adapter = AboutLibraryAdapter(R.layout.item_about_library, arrayList)
        val appDesc = resources.getString(R.string.app_desc)
        val appAddress = resources.getString(R.string.app_address)
        val appLink = resources.getString(R.string.app_link)
        val content = StringBuilder(appDesc)
        content.append(appAddress)
        content.append(appLink)
        val spannableString = SpannableString(content.toString())
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                val bundle = Bundle()
                bundle.putString(WebViewActivity.URL, appLink)
                gotoActivity(mContext as Activity, WebViewActivity().javaClass, bundle)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                //设置文本的颜色
                ds.color = ContextCompat.getColor(mContext, R.color.colorAccent)
                //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线,其实默认也是true，如果要下划线的话可以不设置
                ds.isUnderlineText = true
            }
        }, appDesc.length + appAddress.length, appDesc.length + appAddress.length + appLink.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        appDescTextView.text = spannableString
        // 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
        appDescTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}
