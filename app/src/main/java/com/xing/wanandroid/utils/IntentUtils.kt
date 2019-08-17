package com.xing.wanandroid.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun gotoActivity(activity: Activity, clazz: Class<Any>) {
    val intent = Intent(activity, clazz)
    activity.startActivity(intent)
}

fun gotoActivity(activity: Activity, clazz: Class<Any>, bundle: Bundle) {
    val intent = Intent(activity, clazz)
    intent.putExtras(bundle)
    activity.startActivity(intent)
}