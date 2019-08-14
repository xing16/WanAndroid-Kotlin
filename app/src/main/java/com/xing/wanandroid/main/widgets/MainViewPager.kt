package com.xing.wanandroid.main.widgets

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class MainViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    /**
     * 父容器拦截除 Action_down 外的事件，因为 Action_down 拦截了，后序的事件序列就都分发给父容器了，子 View 不能获取到事件
     *
     * @param ev
     * @return
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
                super.onInterceptTouchEvent(ev)
                return false
            }
        }
        return true
    }
}