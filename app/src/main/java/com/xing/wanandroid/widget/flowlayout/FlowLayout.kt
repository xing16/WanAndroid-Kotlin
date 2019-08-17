package com.xing.wanandroid.widget.flowlayout

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.xing.wanandroid.R

class FlowLayout<T> : ViewGroup {

    private var horizontalMargin: Int = 20
    private var verticalMargin: Int = 0
    private lateinit var adapter: FlowAdapter<T>

    private var childPositions = mutableListOf<FlowChildPosition>()

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        readAttrs(context, attributeSet)
    }

    private fun readAttrs(context: Context, attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout)
        horizontalMargin = typedArray.getDimension(R.styleable.FlowLayout_horizontalMargin, 30f).toInt()
        verticalMargin = typedArray.getDimension(R.styleable.FlowLayout_verticalMargin, 30f).toInt()
        typedArray.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom

        var width = 0
        var height = 0
        var lineWidth = 0
        var lineHeight = 0
        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == View.GONE) {
                childPositions.add(FlowChildPosition(-100, -100, -100, -100))
                continue
            }
            val lp: MarginLayoutParams = childView.layoutParams as MarginLayoutParams
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            val childViewWidth = childView.measuredWidth + lp.leftMargin + lp.rightMargin
            val childViewHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin
            val availableWidth = widthSize - paddingLeft - paddingRight - lineWidth
            if (availableWidth >= childViewWidth) {  // 不换行
                childPositions.add(
                    FlowChildPosition(
                        paddingLeft + lineWidth, paddingTop + height,
                        paddingLeft + lineWidth + childViewWidth,
                        paddingTop + height + childViewHeight
                    )
                )
                lineWidth += (childViewWidth + horizontalMargin)
                width = Math.max(width, lineWidth)
                lineHeight = Math.max(lineHeight, childViewHeight)
            } else {   // 换行
                height += (lineHeight + verticalMargin)
                childPositions.add(
                    FlowChildPosition(
                        paddingLeft,
                        paddingTop + height,
                        paddingLeft + childViewWidth,
                        paddingTop + height + childViewHeight
                    )
                )
                lineWidth = childViewWidth + horizontalMargin
                width = Math.max(width, lineWidth)
                lineHeight = childViewHeight
            }
        }
        val finalWidth =
            if (widthMode == MeasureSpec.EXACTLY) widthSize else (paddingLeft + paddingRight + width - horizontalMargin)
        val finalHeight =
            if (heightMode == MeasureSpec.EXACTLY) heightSize else (lineHeight + height + paddingTop + paddingBottom)
        setMeasuredDimension(finalWidth, finalHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed) {
            for (i in 0 until childCount) {
                if (childPositions[i].left == -100) {
                    continue
                }
                val childView = getChildAt(i)
                val childPosition = childPositions[i]
                childView.layout(childPosition.left, childPosition.top, childPosition.right, childPosition.bottom)
            }
        }
    }

    fun setAdapter(adapter: FlowAdapter<T>) {
        this.adapter = adapter
        val thiz = this
        for (i in 0 until adapter.getCount()) {
            val t: T = adapter.getItem(i)
            val view: View = adapter.getView(i, t, this)
            view.isClickable = true
            view.setOnClickListener { listener.onItemClick(i, adapter, thiz) }
            addView(view)
        }
    }

    private lateinit var listener: OnItemClickListener<T>

    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, adapter: FlowAdapter<T>, parent: FlowLayout<T>)
    }

    fun setOnItemClickListener(onItemClickListener: FlowLayout.OnItemClickListener<T>) {
        this.listener = onItemClickListener
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }


    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }


    private fun dp2px(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    }


}