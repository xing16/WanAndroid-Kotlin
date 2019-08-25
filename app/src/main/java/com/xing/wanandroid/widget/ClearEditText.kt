package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.EditText
import com.xing.wanandroid.R

class ClearEditText : EditText, TextWatcher {

    private var tipDrawable: Drawable? = null
    private var clearDrawable: Drawable? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        readAttrs(context, attributeSet)
        init(context)
    }

    fun readAttrs(context: Context, attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ClearEditText)
        tipDrawable = typedArray.getDrawable(R.styleable.ClearEditText_tipIcon)
        clearDrawable = typedArray.getDrawable(R.styleable.ClearEditText_clearIcon)
        typedArray.recycle()
    }

    fun init(context: Context) {
        compoundDrawablePadding = dp2px(6f).toInt()
        setPadding(dp2px(10f).toInt(), dp2px(10f).toInt(), dp2px(10f).toInt(), dp2px(10f).toInt())
        addTextChangedListener(this)
        setSingleLine()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        tipDrawable?.bounds = Rect(0, 0, h.shr(1), h.shr(1))
        clearDrawable?.bounds = Rect(0, 0, h.shr(1), h.shr(1))

        setCompoundDrawables(tipDrawable, compoundDrawables[1], null, compoundDrawables[3])
    }


    override fun afterTextChanged(s: Editable?) {
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        val content = text.toString().trim()
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            if (TextUtils.isEmpty(content)) null else clearDrawable,
            compoundDrawables[3]
        )
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null) {
            when (event?.action) {
                MotionEvent.ACTION_UP -> {
                    val touchX = event.x
                    val startX = width - paddingRight - compoundDrawables[2].intrinsicWidth - compoundDrawablePadding
                    val endX = width
                    if (touchX > startX && touchX < endX) {
//                            if(hasFocus())
                        setText("")
                        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], null, compoundDrawables[3])
                    }
                }
            }
        }
        return super.onTouchEvent(event)

    }

    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }


}