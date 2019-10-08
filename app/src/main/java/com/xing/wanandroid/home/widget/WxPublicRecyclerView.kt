package com.xing.wanandroid.home.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent

/**
 * 自定义 微信公众号 recyclerview
 */
class WxPublicRecyclerView : RecyclerView {

    private var downX = 0f
    private var downY = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (layoutManager is LinearLayoutManager) {
                    val firstVisiblePosition = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    val deltaX = ev.x - downX
                    // 1. 向左滑动请求父容器不拦截
                    // 2. 向右滑动，并且第一个完全可见的 item 位置不是 0 时，请求不拦截
                    if ((firstVisiblePosition == 0 && deltaX > 0)) {
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
        Log.e("WxPublicRecyclerView", "dispatchTouchEvent = " + ev.action)
        return super.dispatchTouchEvent(ev)
    }
}