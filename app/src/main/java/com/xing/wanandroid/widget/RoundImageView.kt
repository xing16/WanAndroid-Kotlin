package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.*
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.xing.wanandroid.R

class RoundImageView : AppCompatImageView {

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var shape: Int? = 0
    private var shapeRadius: Float = 0f

    private var path: Path = Path()
    private lateinit var rectF: RectF
    private lateinit var paint: Paint
    private lateinit var shapePaint: Paint

    private var porterDuffXfermode: PorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        readAttrs(context, attrs)
        init()
    }

    private fun init() {
        shapePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)

    }

    private fun readAttrs(context: Context?, attrs: AttributeSet?) {
        var typedArray = context?.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        if (typedArray != null) {
            shape = typedArray.getInt(R.styleable.RoundImageView_shape, 0)
            shapeRadius = typedArray.getDimension(R.styleable.RoundImageView_radius, 50f)
            typedArray.recycle()
        } else {
            shape = 0
            shapeRadius = 20f
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        rectF = RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat())

        path.addCircle(mWidth / 2f, mHeight / 2f, Math.min(mWidth, mHeight) / 2f, Path.Direction.CW)

    }

    override fun onDraw(canvas: Canvas?) {
        // 第一种方式 ： clipPath
//        canvas?.save()
//        canvas?.clipPath(path)
//        super.onDraw(canvas)
//        canvas?.restore()

        // 第二种：xfermode
        val layer = canvas?.saveLayer(rectF, null, Canvas.ALL_SAVE_FLAG)
        super.onDraw(canvas)
        paint.xfermode = porterDuffXfermode
        canvas?.drawBitmap(getBitmap(), 0f, 0f, paint)
        paint.xfermode = null
        canvas?.restoreToCount(layer!!)
//        canvas?.drawCircle(mWidth / 2f, mHeight / 2f, Math.min(mWidth, mHeight) / 2f, paint)
//
//
//        canvas?.restoreToCount(layer!!)

//
//        paint.color = Color.RED
////        canvas?.drawRoundRect(rectF, 120f, 120f, paint)
//        canvas?.drawPath(path, paint)
//        paint.xfermode = null
//        canvas?.restoreToCount(layer!!)

    }

    private fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        if (shape == 0) {
            canvas.drawCircle(mWidth / 2f, mHeight / 2f, Math.min(mWidth, mHeight) / 2f, shapePaint)
        } else if (shape == 1) {
            canvas.drawRoundRect(rectF, shapeRadius, shapeRadius, shapePaint)
        }
        return bitmap
    }
}