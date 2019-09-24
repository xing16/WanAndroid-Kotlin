package com.xing.wanandroid.app

import android.app.Application
import android.content.Context
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.ClearableCookieJar


class MainApp : Application() {

    private lateinit var cookieJar: PersistentCookieJar

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