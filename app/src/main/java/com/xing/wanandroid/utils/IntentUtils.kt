package com.xing.wanandroid.utils

import android.app.Activity
import android.content.Intent

fun gotoActivity(activity: Activity, clazz: Class<Any>) {
    val intent = Intent(activity, clazz)
    activity.startActivity(intent)
}