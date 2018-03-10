package com.mentalstack.baseproject.utils.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.mentalstack.baseproject.R

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */

class AspectRatioImageView : ImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        parseAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        parseAttrs(attrs)
    }

    private fun parseAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs,
                R.styleable.AspectRatioImageView, 0, 0)
        ratio = a.getFloat(R.styleable.AspectRatioImageView_ratio, 1.0f)

        a.recycle()
    }

    var ratio: Float = 1.0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, (measuredWidth * ratio).toInt())
    }
}