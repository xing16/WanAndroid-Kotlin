package com.xing.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    lateinit var context: Context

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        context = this
        initView()
        initData()
    }

    open fun initData() {
    }

    abstract fun initView()

    abstract fun getLayoutResId(): Int


}