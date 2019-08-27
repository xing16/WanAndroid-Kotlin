package com.xing.wanandroid.widget

import android.view.View
import android.view.ViewGroup

class ViewMarginWrapper(val view: View) {

    fun setLeftMargin(margin: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        lp.leftMargin = margin
        view.layoutParams = lp
    }

    fun getLeftMargin(): Int {
        val marginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        return marginLayoutParams.leftMargin
    }

    fun setRightMargin(margin: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        lp.rightMargin = margin
        view.layoutParams = lp
    }

    fun getRightMargin(): Int {
        val marginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        return marginLayoutParams.rightMargin
    }


}