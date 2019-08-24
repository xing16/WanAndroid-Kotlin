package com.xing.wanandroid.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jaeger.library.StatusBarUtil
import com.xing.wanandroid.R

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mContext: Context

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        mContext = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//黑色
        }
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorPrimary), 0)
        initView()
        initData()
    }

    open fun initData() {
    }

    abstract fun initView()

    abstract fun getLayoutResId(): Int


}