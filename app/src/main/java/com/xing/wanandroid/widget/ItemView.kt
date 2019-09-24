package com.xing.wanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.xing.wanandroid.R

class ItemView : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {

        init(context, attributeSet)
    }

    private fun init(context: Context, attributeSet: AttributeSet?) {
        readAttrs(context, attributeSet)
        LayoutInflater.from(context).inflate(R.layout.layout_item_view, this, true)

    }

    private fun readAttrs(context: Context, attributeSet: AttributeSet?) {
    }


}