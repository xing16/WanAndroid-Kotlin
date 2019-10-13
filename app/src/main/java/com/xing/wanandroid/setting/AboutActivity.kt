package com.xing.wanandroid.setting

import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.textclassifier.TextLinks
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xing.wanandroid.R
import com.xing.wanandroid.base.BaseActivity
import com.xing.wanandroid.setting.adapter.AboutLibraryAdapter
import com.xing.wanandroid.utils.dp2px
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
        spannableString.setSpan(URLSpan("https://github.com/xing16/WanAndroid-Kotlin"), appDesc.length + appAddress.length, appDesc.length + appAddress.length + appLink.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        appDescTextView.text = spannableString
        // 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象
        appDescTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}
