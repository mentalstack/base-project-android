package com.mentalstack.baseproject.utils.ui

import android.text.method.PasswordTransformationMethod
import android.view.View

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */
class PasswordStarTransformMethod : PasswordTransformationMethod() {
    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(source)
    }
}

private class PasswordCharSequence(private var mSource: CharSequence) : CharSequence {
    override val length: Int
        get() = mSource.length

    override fun get(index: Int): Char = '*'

    override fun subSequence(start: Int, end: Int) = mSource.subSequence(start, end)
}