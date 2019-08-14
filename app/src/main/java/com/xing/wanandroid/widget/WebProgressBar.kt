package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.xing.wanandroid.R

class WebProgressBar : View {

    private var progress: Int = 0
    private var mHeight: Float = 3f
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        paint.style = Paint.Style.FILL
        paint.strokeWidth = mHeight
        paint.color = resources.getColor(R.color.colorPrimary)
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
        canvas?.drawRect(0f, 0f, width * progress / 100f, mHeight, paint)
    }
}