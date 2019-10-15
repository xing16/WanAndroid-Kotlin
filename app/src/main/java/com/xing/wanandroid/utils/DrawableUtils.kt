package com.xing.wanandroid.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.xing.wanandroid.R

private val colors: Array<Int> = arrayOf(
    R.color.color_d67391,
    R.color.color_f5d8ad72,
    R.color.color_8299a3,
    R.color.color_7f7099,
    R.color.color_5b8f8a
)

fun createColorDrawable(context: Context): ColorDrawable {
    val random = (0 until colors.size).random()
    Log.e("debugdebug", "random = $random")
    return ColorDrawable(ContextCompat.getColor(context, colors[random]))
}