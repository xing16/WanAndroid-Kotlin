package com.xing.wanandroid.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator


class LoginView : View {

    private var backgroundPaint: Paint
    private var arcPaint: Paint
    private lateinit var textPaint: Paint
    private var bgColor: Int = 0xffff0000.toInt()
    private var text: String = ""
    private var textColor: Int = 0xffffffff.toInt()
    private var textSize: Float = dp2px(16f)
    private var arcColor: Int = 0xffffffff.toInt()
    private var arcStrokeWidth: Float = dp2px(5f)
    private var mViewWidth: Int = 0
    private var mViewHeight: Int = 0
    private var viewRectF: RectF = RectF()
    private var arcRectF: RectF = RectF()
    private var radius: Float = dp2px(5f)
    private var mCurWidth: Float = 0f
    private var mCurRadius: Float = 0f
    private var mCurAlpha: Int = 255
    private var isShowLoading = false
    private var baseLine = 0f


    private var rotateDelta = 4
    private var curAngle = 0
    private var minAngle = -90
    private var startAngle = -90  // 上方顶点
    private var endAngle = 0


    companion object {
        const val STATE_LOADING = 1
        const val STATE_FAILED = 2

    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        readAttrs(context, attributeSet)
    }

    private fun readAttrs(context: Context, attributeSet: AttributeSet?) {
        val typeArray =
            context.obtainStyledAttributes(attributeSet, com.xing.wanandroid.R.styleable.LoginView)
        bgColor = typeArray.getColor(
            com.xing.wanandroid.R.styleable.LoginView_bgColor,
            0xffff0000.toInt()
        )
        radius = typeArray.getDimension(
            com.xing.wanandroid.R.styleable.LoginView_borderRadius,
            dp2px(6f)
        )
        arcColor = typeArray.getColor(
            com.xing.wanandroid.R.styleable.LoginView_arcColor,
            0xffffffff.toInt()
        )
        arcStrokeWidth = typeArray.getDimension(
            com.xing.wanandroid.R.styleable.LoginView_arcStrokeWidth,
            dp2px(3f)
        )
        text = typeArray.getString(com.xing.wanandroid.R.styleable.LoginView_text) ?: ""
        textColor = typeArray.getColor(
            com.xing.wanandroid.R.styleable.LoginView_textColor,
            0xffffffff.toInt()
        )
        textSize =
            typeArray.getDimension(com.xing.wanandroid.R.styleable.LoginView_textSize, dp2px(18f))
        typeArray.recycle()
    }

    init {
        backgroundPaint = createPaint(bgColor, Paint.Style.FILL, 0f)
        arcPaint = createPaint(arcColor, Paint.Style.STROKE, dp2px(3f))
        textPaint = createPaint(textColor, Paint.Style.FILL, 0f)
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = if (heightMode == MeasureSpec.EXACTLY) heightSize else dp2px(50f).toInt()
        setMeasuredDimension(widthMeasureSpec, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHeight = h

        mCurWidth = mViewWidth.toFloat()
        mCurRadius = radius
        baseLine = -(textPaint.descent() + textPaint.ascent()) / 2f

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.translate(mViewWidth / 2f, mViewHeight / 2f)
        viewRectF.set(-mCurWidth / 2f, -mViewHeight / 2f, mCurWidth / 2f, mViewHeight / 2f)
        canvas?.drawRoundRect(viewRectF, mCurRadius, mCurRadius, backgroundPaint)
        if (isShowLoading) {
            drawArcLoading(canvas)
        }
        textPaint.alpha = mCurAlpha
        canvas?.drawText(text, 0f, baseLine, textPaint)
        canvas?.restore()
    }

    private fun drawArcLoading(canvas: Canvas?) {
        canvas?.save()
        if (endAngle >= 300 || startAngle > minAngle) {
            startAngle += 6
            if (endAngle > 20) {
                endAngle -= 6
            }
        }
        if (startAngle > minAngle + 300) {
            minAngle = startAngle
            endAngle = 20
        }
        curAngle += rotateDelta
        canvas?.rotate(curAngle.toFloat(), 0f, 0f)//旋转rotateDelta=4的弧长
        canvas?.drawArc(
            RectF(
                -mViewHeight / 4f,
                -mViewHeight / 4f,
                mViewHeight / 4f,
                mViewHeight / 4f
            ), startAngle.toFloat(), endAngle.toFloat(), false, arcPaint
        )
        // endAngle += 6 放在 drawArc()后面，是防止刚进入时，突兀的显示了一段圆弧
        if (startAngle == minAngle) {
            endAngle += 6
        }
        invalidate()
        canvas?.restore()
    }

    fun setState(state: Int) {
        if (state == STATE_LOADING) {
//            startRadiusAnimation(radius, mViewHeight / 2f)
            shrinkAnimation(
                radius,
                mViewHeight / 2f,
                mViewWidth.toFloat(),
                mViewHeight.toFloat(),
                300L
            )
        } else if (state == STATE_FAILED) {
            isShowLoading = false
            expandAnimation(
                mViewHeight / 2f,
                radius,
                mViewHeight.toFloat(),
                mViewWidth.toFloat(),
                300
            )
        }
    }

    /**
     * 收缩动画
     */
    private fun shrinkAnimation(
        startRadius: Float, endRadius: Float, startWidth: Float, endWidth: Float,
        duration: Long
    ) {
        // 圆角半径动画
        val radiusAnimation = ValueAnimator.ofFloat(startRadius, endRadius)
        radiusAnimation.duration = duration
        radiusAnimation.interpolator = AccelerateInterpolator()
        radiusAnimation.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurRadius = animation?.animatedValue as Float
                invalidate()
            }
        })

        // 宽度变化动画
        val widthAnimation = ValueAnimator.ofFloat(startWidth, endWidth)
        widthAnimation.duration = duration
        widthAnimation.interpolator = AccelerateInterpolator()
        widthAnimation.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurWidth = animation?.animatedValue as Float
            }
        })

        val textAlphaAnimation = ValueAnimator.ofInt(255, 0)
        textAlphaAnimation.duration = duration
        textAlphaAnimation.interpolator = AccelerateInterpolator()
        textAlphaAnimation.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurAlpha = animation?.animatedValue as Int
            }
        })

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(radiusAnimation, widthAnimation, textAlphaAnimation)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                isShowLoading = true
            }
        })
        animatorSet.start()
    }

    /**
     * 展开动画
     */
    private fun expandAnimation(
        startRadius: Float, endRadius: Float, startWidth: Float, endWidth: Float,
        duration: Long
    ) {

        val widthAnimation = ValueAnimator.ofFloat(startWidth, endWidth)
        widthAnimation.duration = duration
        widthAnimation.interpolator = AccelerateInterpolator()
        widthAnimation.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurWidth = animation?.animatedValue as Float
                invalidate()
            }
        })

        val radiusAnimation = ValueAnimator.ofFloat(startRadius, endRadius)
        radiusAnimation.duration = duration
        radiusAnimation.interpolator = AccelerateInterpolator()
        radiusAnimation.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurRadius = animation?.animatedValue as Float
            }
        })

        val textAlphaAnimation = ValueAnimator.ofInt(0, 255)
        textAlphaAnimation.duration = duration
        textAlphaAnimation.interpolator = AccelerateInterpolator()
        textAlphaAnimation.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurAlpha = animation?.animatedValue as Int
            }
        })


        val animatorSet = AnimatorSet()
        animatorSet.playTogether(widthAnimation, radiusAnimation,textAlphaAnimation)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                isShowLoading = false
            }
        })
        animatorSet.start()
    }

    private fun createPaint(color: Int, style: Paint.Style, strokeWidth: Float): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        paint.style = style
        paint.strokeWidth = strokeWidth
        return paint
    }

    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }
}