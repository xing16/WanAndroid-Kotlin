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

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
//        addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (layoutManager is LinearLayoutManager) {
//                    val firstVisiblePosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//                    Log.e("WxPublicRecyclerView", "firstVisiblePosition============= = ${firstVisiblePosition}")
//                }
//            }
//        })
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("WxPublicRecyclerView", "ACTION_DOWN")
                downX = ev.x
                downY = ev.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("WxPublicRecyclerView111", "ACTION_MOVE")
                setOnScrollListener(object : OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (layoutManager is LinearLayoutManager) {
                            val firstVisiblePosition = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                            Log.e("WxPublicRecyclerView", "firstVisiblePosition = ${firstVisiblePosition}")
                            if (firstVisiblePosition == 2 && (ev.x > downX)) {
                                parent.requestDisallowInterceptTouchEvent(false)
                            }
                        }
                    }
                })

            }
            MotionEvent.ACTION_UP -> {
                Log.e("WxPublicRecyclerView", "ACTION_UP")
            }
        }

        return super.dispatchTouchEvent(ev)
    }
}