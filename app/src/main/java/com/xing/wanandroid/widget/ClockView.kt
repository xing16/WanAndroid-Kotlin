package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*

class ClockView : View {

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var raius: Float = 0f
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var month: Int = 1
    private var day: Int = 1
    private val calendar: Calendar = Calendar.getInstance()


    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        paint.textSize = dp2px(16f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        raius = Math.min(mWidth, mHeight) / 2f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width: Int = getMeasuredSize(widthMeasureSpec)
        val height: Int = getMeasuredSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun getMeasuredSize(measureSpec: Int): Int {
        val mode: Int = MeasureSpec.getMode(measureSpec)
        val size: Int = MeasureSpec.getSize(measureSpec)
        var value = 0
        if (mode == MeasureSpec.EXACTLY) {
            value = size
        } else {
            if (mode == MeasureSpec.AT_MOST) {
                value = Math.min(dp2px(100f), size.toFloat()).toInt()
            }
        }
        return value
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.drawColor(Color.BLACK)

        canvas?.translate(mWidth / 2f, mHeight / 2f)
        val second = calendar.get(Calendar.SECOND)


        for (i in 0 until 60) {
            canvas?.drawText("${i} 秒", 400f, 0f, paint)
            canvas?.drawText("${i} 分", 280f, 0f, paint)
            if (i % 5 == 0) {
                canvas?.drawText("${i / 5}时", 200f, 0f, paint)
                canvas?.drawText("${i / 5}月", 50f, 0f, paint)
            }
            if (i % 31 == 0) {

            }

            canvas?.rotate(6f)

        }









        canvas?.restore()
    }

    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }


}