package com.xing.wanandroid.common.cookie

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Cookie
import java.util.concurrent.ConcurrentHashMap

class PersistentCookieStore {

    private val COOKIE_PREFS = "Cookies_Prefs"
    private var cookiesMap: Map<String, ConcurrentHashMap<String, Cookie>>
    private var cookiePrefs: SharedPreferences

    constructor(context: Context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE)
        cookiesMap = HashMap()

        // 将持久化的 cookies 缓存到内存中，即 cookiesMap
        val prefsMap: Map<String, *> = cookiePrefs.all
        prefsMap.entries

    }


}