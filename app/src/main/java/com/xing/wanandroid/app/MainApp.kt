package com.xing.wanandroid.app

import android.app.Application
import android.content.Context

class MainApp : Application() {


    companion object {
        private lateinit var context: Context

        public fun getContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}