package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xing.wanandroid.utils.dp2px

class LinearItemDecoration : RecyclerView.ItemDecoration {

    private var mContext: Context
    private var paint: Paint
    private var leftMargin: Int = 0
    private var rightMargin: Int = 0
    private var height: Int = 0
    private var itemOffsetLeft: Int = 0
    private var itemOffsetRight: Int = 0
    private var jumpPositions = ArrayList<Int>()

    constructor(context: Context) : super() {
        mContext = context
        height = dp2px(context, 1f).toInt()
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
    }

    /**
     * 设置 ItemDecoration 左右宽度，设置 getItemOffsets() 中的 outRect.left, outRect.right
     */
    fun itemOffsets(itemOffsetLeft: Float, itemOffsetRight: Float): LinearItemDecoration {
        this.itemOffsetLeft = dp2px(mContext, itemOffsetLeft).toInt()
        this.itemOffsetRight = dp2px(mContext, itemOffsetRight).toInt()
        return this
    }

    /**
     * 设置 ItemDecoration 高度
     */
    fun height(height: Float): LinearItemDecoration {
        this.height = dp2px(mContext, height).toInt()
        return this
    }

    /**
     * 设置 ItemDecoration 颜色
     */
    fun color(color: Int): LinearItemDecoration {
        paint.color = color
        return this
    }

    fun margin(left: Float, right: Float): LinearItemDecoration {
        this.leftMargin = dp2px(mContext, left).toInt()
        this.rightMargin = dp2px(mContext, right).toInt()
        return this
    }

    /**
     * 跳过指定位置，不设置分割线
     */
    fun jumpPositions(positions: Array<Int>): LinearItemDecoration {
        for (i in 0 until positions.size) {
            jumpPositions.add(i)
        }
        return this
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = itemOffsetLeft
        outRect.right = itemOffsetRight
        // 当前 item 的 position
        val childLayoutPosition: Int = parent.getChildLayoutPosition(view)
        // 最后一条 item 的 position
        val lastItemPosition: Int = state.itemCount - 1
        /**
         * 因为是 设置的 outRect.bottom , 所以最后一条不设置底部分割线
         */
        if (jumpPositions.contains(childLayoutPosition) or (childLayoutPosition == lastItemPosition)) {
            outRect.bottom = 0
        } else {
            outRect.bottom = height
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val layoutManager: RecyclerView.LayoutManager? = parent.layoutManager
        val paddingLeft = parent.paddingLeft
        val paddingRight = parent.paddingRight
        for (i in 0 until parent.childCount) {
            val childView: View = parent.getChildAt(i)
            var leftDecorationWidth: Int = layoutManager?.getLeftDecorationWidth(childView) ?: 0
            val rightDecorationWidth: Int = layoutManager?.getRightDecorationWidth(childView) ?: 0
            val bottomDecorationHeight: Int = layoutManager?.getBottomDecorationHeight(childView) ?: 0
            val left = leftDecorationWidth + paddingLeft + leftMargin
            val right = parent.right - rightDecorationWidth - paddingRight - rightMargin
            val top = childView.bottom
            val bottom = childView.bottom + bottomDecorationHeight
            c.drawRect(RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat()), paint)
        }
    }
}