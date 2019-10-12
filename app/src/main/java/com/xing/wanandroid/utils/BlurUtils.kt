package com.xing.wanandroid.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.renderscript.Allocation
import androidx.renderscript.Element
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur

/**
 * 0 - 25
 */
fun blur(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
    val rs = RenderScript.create(context)
    val outputBitmap = Bitmap.createBitmap(bitmap)
    val input = Allocation.createFromBitmap(rs, bitmap)
    val output = Allocation.createFromBitmap(rs, bitmap)
    val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    scriptIntrinsicBlur.setRadius(radius.toFloat())
    scriptIntrinsicBlur.setInput(input)
    scriptIntrinsicBlur.forEach(output)
    output.copyTo(outputBitmap)
    return outputBitmap

}

