package com.xing.wanandroid.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.xing.wanandroid.R


class LoginView : View {

    private lateinit var backgroundPaint: Paint
    private lateinit var arcPaint: Paint
    private lateinit var textPaint: Paint
    private var bgColor: Int = Color.RED
    private var text: String = "登录"
    private var textColor: Int = 0
    private var textSize: Float = dp2px(22f)
    private var arcColor: Int = 0
    private var arcStrokeWidth: Float = dp2px(5f)
    private var mViewWidth: Int = 0
    private var mViewHeight: Int = 0
    private var viewRectF: RectF = RectF()
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

    private var radiusAnimation: ValueAnimator? = null
    private var widthAnimation: ValueAnimator? = null
    private var textAlphaAnimation: ValueAnimator? = null
    private var animatorSet: AnimatorSet? = null
    private var isReleased: Boolean = false


    companion object {
        const val STATE_LOADING = 1
        const val STATE_FAILED = 2
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        readAttrs(context, attributeSet)
        init()
    }

    private fun readAttrs(context: Context, attributeSet: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.LoginView)
        bgColor = typeArray.getColor(R.styleable.LoginView_bgColor, 0)
        radius = typeArray.getDimension(R.styleable.LoginView_borderRadius, dp2px(6f))
        arcColor = typeArray.getColor(R.styleable.LoginView_arcColor, 0xffffffff.toInt())
        arcStrokeWidth = typeArray.getDimension(R.styleable.LoginView_arcStrokeWidth, dp2px(3f))
        text = typeArray.getString(R.styleable.LoginView_text) ?: ""
        textColor = typeArray.getColor(R.styleable.LoginView_textColor, 0xffffffff.toInt())
        textSize = typeArray.getDimension(R.styleable.LoginView_textSize, sp2px(16f))
        typeArray.recycle()
    }

    private fun init() {
        backgroundPaint = createPaint(bgColor, Paint.Style.FILL, 0f)
        arcPaint = createPaint(arcColor, Paint.Style.STROKE, dp2px(3f))
        textPaint = createPaint(textColor, Paint.Style.FILL, 10f)
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
        if (isReleased) {
            return
        }
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
            RectF(-mViewHeight / 4f, -mViewHeight / 4f, mViewHeight / 4f, mViewHeight / 4f),
            startAngle.toFloat(),
            endAngle.toFloat(),
            false,
            arcPaint
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
            shrinkAnimation(radius, mViewHeight / 2f, mViewWidth.toFloat(), mViewHeight.toFloat(), 300L)
        } else if (state == STATE_FAILED) {
            isShowLoading = false
            expandAnimation(mViewHeight / 2f, radius, mViewHeight.toFloat(), mViewWidth.toFloat(), 300L)
        }
    }


    fun release() {
        radiusAnimation?.cancel()
        widthAnimation?.cancel()
        textAlphaAnimation?.cancel()
        isReleased = true
    }

    /**
     * 收缩动画
     */
    private fun shrinkAnimation(startRadius: Float, endRadius: Float, startWidth: Float, endWidth: Float, duration: Long) {
        // 圆角半径动画
        radiusAnimation = ValueAnimator.ofFloat(startRadius, endRadius)
        radiusAnimation?.duration = duration
        radiusAnimation?.interpolator = AccelerateInterpolator()
        radiusAnimation?.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurRadius = animation?.animatedValue as Float
                invalidate()
            }
        })

        // 宽度变化动画
        widthAnimation = ValueAnimator.ofFloat(startWidth, endWidth)
        widthAnimation?.duration = duration
        widthAnimation?.interpolator = AccelerateInterpolator()
        widthAnimation?.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurWidth = animation?.animatedValue as Float
            }
        })

        textAlphaAnimation = ValueAnimator.ofInt(255, 0)
        textAlphaAnimation?.duration = duration
        textAlphaAnimation?.interpolator = AccelerateInterpolator()
        textAlphaAnimation?.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurAlpha = animation?.animatedValue as Int
            }
        })

        animatorSet = AnimatorSet()
        animatorSet?.playTogether(radiusAnimation, widthAnimation, textAlphaAnimation)
        animatorSet?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                isShowLoading = true
            }
        })
        animatorSet?.start()
    }

    /**
     * 展开动画
     */
    private fun expandAnimation(startRadius: Float, endRadius: Float, startWidth: Float, endWidth: Float, duration: Long) {
        widthAnimation = ValueAnimator.ofFloat(startWidth, endWidth)
        widthAnimation?.duration = duration
        widthAnimation?.interpolator = AccelerateInterpolator()
        widthAnimation?.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurWidth = animation?.animatedValue as Float
                invalidate()
            }
        })

        radiusAnimation = ValueAnimator.ofFloat(startRadius, endRadius)
        radiusAnimation?.duration = duration
        radiusAnimation?.interpolator = AccelerateInterpolator()
        radiusAnimation?.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurRadius = animation?.animatedValue as Float
            }
        })

        textAlphaAnimation = ValueAnimator.ofInt(0, 255)
        textAlphaAnimation?.duration = duration
        textAlphaAnimation?.interpolator = AccelerateInterpolator()
        textAlphaAnimation?.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                mCurAlpha = animation?.animatedValue as Int
            }
        })

        animatorSet = AnimatorSet()
        animatorSet?.playTogether(widthAnimation, radiusAnimation, textAlphaAnimation)
        animatorSet?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                isShowLoading = false
            }
        })
        animatorSet?.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (animatorSet?.isRunning == true) {
            return false
        }
        return super.onTouchEvent(event)

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

    private fun sp2px(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
    }
}