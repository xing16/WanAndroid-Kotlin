package com.xing.wanandroid.utils

import android.view.View
import android.view.ViewTreeObserver

class KeyboardWatcher : ViewTreeObserver.OnGlobalLayoutListener {

    private var listener = ArrayList<KeyboardStateListener>()
    private lateinit var rootView: View
    private var lastKeyboardHeight: Int = 0
    private var isKeyboardOpend = false
    private var statusBarHeight = 0

    constructor(rootView: View) : this(rootView, false)

    constructor(rootView: View, isKeyboardOpened: Boolean) {
        this.rootView = rootView
        this.isKeyboardOpend = isKeyboardOpend
        rootView.viewTreeObserver.addOnGlobalLayoutListener(this)
        val resourceId = rootView.context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = rootView.context.resources.getDimensionPixelSize(resourceId)
        }
    }

    interface KeyboardStateListener {
        fun onKeyboardOpened(keyboardHeight: Int)
        fun onKeyboardClosed()
    }


    override fun onGlobalLayout() {

    }


}