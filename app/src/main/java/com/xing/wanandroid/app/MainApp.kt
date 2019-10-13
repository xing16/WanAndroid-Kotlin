package com.xing.wanandroid.app

import android.app.Application
import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.xing.wanandroid.R


class MainApp : Application() {

    private lateinit var cookieJar: PersistentCookieJar

    init {
        //设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(context: Context, layout: RefreshLayout): RefreshHeader {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.black)  //全局设置主题颜色
                return ClassicsHeader(Companion.context)//指定为经典Header，默认是 贝塞尔雷达Header
            }
        })

        // 设置全局的 Footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(context: Context, layout: RefreshLayout): RefreshFooter {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)  //全局设置主题颜色
                return ClassicsFooter(Companion.context)   //指定为经典Header，默认是 贝塞尔雷达Header
            }
        })
    }

    companion object {
        private lateinit var context: Context

        private lateinit var instance: MainApp

        fun getContext(): Context {
            return context.applicationContext
        }

        fun getInstance(): MainApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        instance = this
        cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
    }

    fun getPersistentCookieJar(): PersistentCookieJar {
        return cookieJar
    }
}