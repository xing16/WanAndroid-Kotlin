package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.xing.wanandroid.R

class RoundImageView : AppCompatImageView {

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var shape: Int? = 0
    private var shapeRadius: Float = 0f
    private var borderColor: Int = 0
    private var borderWidth: Float = 0f

    private var path: Path = Path()
    private lateinit var layerRectF: RectF
    private lateinit var shapeRectF: RectF
    private lateinit var bitmapPaint: Paint
    private lateinit var shapePaint: Paint
    private lateinit var borderPaint: Paint

    private var porterDuffXfermode: PorterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        readAttrs(context, attrs)
        init()
    }

    private fun init() {
        shapePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth
        borderPaint.style = Paint.Style.STROKE
    }

    private fun readAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        shape = typedArray.getInt(R.styleable.RoundImageView_shape, 0)
        shapeRadius = typedArray.getDimension(R.styleable.RoundImageView_radius, 0f)
        borderColor = typedArray.getColor(R.styleable.RoundImageView_borderColor, 0xffff0000.toInt())
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_borderWidth, 0).toFloat()
        typedArray.recycle()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        centerX = mWidth / 2f
        centerY = mHeight / 2f

        layerRectF = RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat())
        shapeRectF = RectF(
            borderPaint.strokeWidth / 2f,
            borderPaint.strokeWidth / 2f,
            mWidth.toFloat() - borderPaint.strokeWidth / 2f,
            mHeight.toFloat() - borderPaint.strokeWidth / 2f
        )

//        path.addCircle(centerX, centerY, Math.min(mWidth, mHeight) / 2f, Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas?) {
        // 第一种方式 ： clipPath
//        canvas?.save()
//        canvas?.clipPath(path)
//        super.onDraw(canvas)
//        canvas?.restore()

        // 第二种：xfermode
        val layer = canvas?.saveLayer(layerRectF, null, Canvas.ALL_SAVE_FLAG)
        super.onDraw(canvas)
        bitmapPaint.xfermode = porterDuffXfermode
        canvas?.drawBitmap(getBitmap(), 0f, 0f, bitmapPaint)
        bitmapPaint.xfermode = null
        canvas?.restoreToCount(layer!!)
        if (borderWidth != 0f) {
            if (shape == 0) {
                canvas?.drawCircle(centerX, centerY, Math.min(mWidth, mHeight) / 2f - borderPaint.strokeWidth / 2f, borderPaint)
            } else if (shape == 1) {
                canvas?.drawRoundRect(shapeRectF, shapeRadius, shapeRadius, borderPaint)
            }
        }
    }

    private fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        if (shape == 0) {
            canvas.drawCircle(centerX, centerY, Math.min(mWidth, mHeight) / 2f, shapePaint)
        } else if (shape == 1) {
            canvas.drawRoundRect(shapeRectF, shapeRadius, shapeRadius, shapePaint)
        }
        return bitmap
    }
}