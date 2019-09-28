package com.xing.wanandroid.utils

import android.content.Context
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

/**
 * 判断 Cookie 是否为空
 */
fun isCookieNotEmpty(context: Context): Boolean {
    val spp = SharedPrefsCookiePersistor(context)
    val cookiesList = spp.loadAll()
    if (cookiesList.isEmpty()) {
        return false
    }
    return true
}