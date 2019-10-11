package com.xing.wanandroid.main.widgets

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import kotlin.math.abs

/**
 * viewpager 与 recyclerview 之间的滑动冲突，使用外部拦截法
 */
class MainViewPager : ViewPager {

    private var downX = 0f
    private var downY = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        super.onInterceptTouchEvent(ev)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                return false
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                if ((abs(ev.x - downX) > abs(ev.y - downY))) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


//    /**
//     * 1. 父容器拦截除 Action_down 外的事件，因为如果拦截了 Action_Down 事件，则后序的事件序列就全部交给了父容器，子 View 无法在接收到事件
//     * 2. move 事件，水平滑动距离大于竖直滑动距离 viewpager 才进行拦截，否则交给 recyclerview
//     *
//     *
//     * @param ev
//     * @return
//     */
//    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        when (ev.action) {
//            // down 事件不拦截
//            MotionEvent.ACTION_DOWN -> {
//                downX = ev.x
//                downY = ev.y
//                super.onInterceptTouchEvent(ev)
//                return false
//            }
//            // up 事件不拦截
//            MotionEvent.ACTION_UP -> {
//                super.onInterceptTouchEvent(ev)
//                return false
//            }
//            // move 事件在水平滑动距离大于竖直滑动距离时才拦截
//            MotionEvent.ACTION_MOVE -> {
//                if ((abs(ev.x - downX) > abs(ev.y - downY))) {
//                    return true
//                }
//                return false
//            }
//        }
//        return false
//    }


}