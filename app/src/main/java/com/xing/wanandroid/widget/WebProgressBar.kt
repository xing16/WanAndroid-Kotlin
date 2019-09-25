package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.xing.wanandroid.R

class WebProgressBar : View {

    private var progress: Int = 0
    private var mHeight: Int = 0
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        paint.style = Paint.Style.FILL
        paint.color = resources.getColor(R.color.colorAccent)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeight = h
    }


    fun setProgress(newProgress: Int) {
        progress = newProgress
        invalidate()
    }

    fun getProgress(): Int {
        return progress
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.strokeWidth = mHeight.toFloat()
        canvas?.drawLine(0f, 0f, width * progress / 100f, 0f, paint)
    }
}