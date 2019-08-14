package com.xing.wanandroid.utils

import java.text.SimpleDateFormat
import java.util.*

fun format(time: Long): String {
    var date = Date(time)
    var sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(date)
}