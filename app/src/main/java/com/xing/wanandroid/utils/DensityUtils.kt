package com.xing.wanandroid.utils

import android.content.Context
import android.util.TypedValue

fun dp2px(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}