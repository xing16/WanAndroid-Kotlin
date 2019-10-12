package com.xing.wanandroid.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import com.xing.wanandroid.R
import com.xing.wanandroid.common.annotation.EventBusSubscribe
import com.xing.wanandroid.utils.EventBusUtils

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
        if (isEventBusAnnotationPresent()) {
            EventBusUtils.register(this)
        }
        initView()
        initData()
    }


    open fun initData() {
    }

    abstract fun initView()

    abstract fun getLayoutResId(): Int

    private fun isEventBusAnnotationPresent(): Boolean {
        return javaClass.isAnnotationPresent(EventBusSubscribe::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isEventBusAnnotationPresent()) {
            EventBusUtils.unregister(this)
        }
    }


}