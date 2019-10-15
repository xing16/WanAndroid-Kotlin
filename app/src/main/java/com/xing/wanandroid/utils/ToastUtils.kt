package com.xing.wanandroid.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast

class ToastUtils {

    companion object {
        var toast: Toast? = null
        fun show(context: Context, resId: Int) {
            if (toast == null) {
                toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
            }
            toast?.cancel()
            toast?.setText(resId)
            toast?.show()
        }

        fun show(context: Context, view: View) {
            if (toast == null) {
                toast = Toast(context)
            }
            toast?.cancel()
            toast?.view = view
            toast?.setGravity(Gravity.CENTER, 0, 0)
            toast?.show()
        }
    }
}