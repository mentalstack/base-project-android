package com.mentalstack.baseproject.utils.ui

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.mentalstack.baseproject.utils.nativeData.Regulars

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */

/**
 * replace visible characters by dots
 */
var EditText.asPassword: Boolean
    get() = inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD
    set(value) {
        inputType = if (value)
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        else
            InputType.TYPE_CLASS_TEXT
        transformationMethod = if (value)
            PasswordStarTransformMethod()
        else
            null
    }

/**
 * Listen focus lose
 */
fun EditText.onFocusLoseListener(listener: (View) -> Unit) {
    this.setOnFocusChangeListener { view, hasFocus ->
        if (!hasFocus) {
            listener(view)
        }
    }
}

fun EditText.setLineColor(color: Int) {
    val colorStateList = ColorStateList.valueOf(color)
    ViewCompat.setBackgroundTintList(this, colorStateList)
}

fun EditText.setLineColorByID(id: Int) {
    setLineColor(ContextCompat.getColor(context, id))
}

/**
 * Update text color from regular expression
 * @param regular - expression
 * @param confirmColor - color from positive result
 * @param rejectColor - color from negative result
 */
fun EditText.addCheckFromRegular(regular: Regex, confirmColor: Int, rejectColor: Int) {
    onTextChanged {
        if (regular.matches(it))
            setLineColor(confirmColor)
        else
            setLineColor(rejectColor)
    }
}

/**
 * Update text color from regular expression
 * @param regular - expression
 * @param confirmColorID - colorID from positive result
 * @param rejectColorID - colorID from negative result
 */
fun EditText.addCheckFromRegularWithID(regular: Regex, confirmColorID: Int, rejectColorID: Int) {
    addCheckFromRegular(regular, context.color(confirmColorID), context.color(rejectColorID))
}

fun EditText.addCMailCheckWithID(confirmColorID: Int, rejectColorID: Int) {
    addCMailCheck(context.color(confirmColorID), context.color(rejectColorID))
}

fun EditText.addCMailCheck(confirmColor: Int, rejectColor: Int) {
    addCheckFromRegular(Regulars.mail.reg, confirmColor, rejectColor)
}

/**
 * listen text updates
 */
fun EditText.onTextChanged(callback: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            callback(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })
}

fun EditText.colorize(resId: Int) {
    setTextColor(ContextCompat.getColor(context, resId))
}

/**
 * set max length of input characters
 */
var EditText.maxLength: Int
    set(value) {
        filters = arrayOf(InputFilter.LengthFilter(value))
    }
    get() {
        filters?.forEach {
            (it as? InputFilter.LengthFilter)?.let {
                return it.max
            }
        }
        return 0
    }

