package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class RingView : View {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var radius: Float = 0f
    private var porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    private lateinit var rect: RectF

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet) {
        paint.color = 0xffff0000.toInt()
        paint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        radius = Math.min(mWidth, mHeight) / 2f
        rect = RectF(-radius, -radius, radius, radius)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()
        canvas?.translate(mWidth / 2f, mHeight / 2f)
        val saveLayer: Int =
            canvas!!.saveLayer(-radius, -radius, radius, radius, paint, Canvas.ALL_SAVE_FLAG)

        // 绘制 dest
        canvas?.drawCircle(0f, 0f, radius, paint)
        // 设置 xfermode
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        paint.color = 0xff00ffff.toInt()
//        // 绘制 src
        canvas?.drawRect(rect, paint)
        paint.xfermode = null
        canvas.restoreToCount(saveLayer)

    }


}