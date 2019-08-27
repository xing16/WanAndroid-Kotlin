package com.xing.wanandroid.widget

import android.view.View

class ViewWidthWrapper(val view: View) {

    fun setWidth(width: Int) {
        val lp = view.layoutParams
        lp.width = width
        view.layoutParams = lp
    }


    fun getWidth(): Int {
        return view.layoutParams.width
    }
}