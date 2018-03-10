package com.mentalstack.baseproject.utils.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.view.View

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */
var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value)
            View.VISIBLE
        else
            View.INVISIBLE
    }

var View.gone: Boolean
    get() = visibility == View.GONE
    set(value) {
        visibility = if (value)
            View.GONE
        else
            View.VISIBLE
    }

fun View.drawFromBitmap(): Bitmap? {
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val b = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    layout(left, top, right, bottom)
    draw(c)
    return b
}

fun View.color(resId: Int) = ContextCompat.getColor(context, resId)
fun View.str(resId: Int) = context.getString(resId)