package com.mentalstack.baseproject.utils.ui

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.runOnUiThread

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */
fun AppCompatActivity.safety(block: (AppCompatActivity) -> Unit) {
    if (!isDestroyed) {
        runOnUiThread {
            if (!isDestroyed)
                block(this)
        }
    }
}

fun Fragment.safety(block: (Fragment) -> Unit) {
    if (!isDetached) {
        activity?.let {
            activity.runOnUiThread {
                if (!isDetached) {
                    block(this)
                }
            }
        }
    }
}

fun Context.safety(some: () -> Unit) {
    (this as? AppCompatActivity)?.let {
        some()
    } ?: runOnUiThread { some() }
}

fun Fragment.hideKeyboard() = activity.hideKeyboard(activity.currentFocus)
fun Activity.hideKeyboard() = hideKeyboard(currentFocus)
fun AppCompatActivity.hideKeyboard() = hideKeyboard(currentFocus)

private fun Context.hideKeyboard(currentFocus: View?) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.let {
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun AppCompatActivity.showOnce(clazz: Class<*>) {
    val int = Intent(this, clazz)
    int.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    startActivity(int)
}

fun AppCompatActivity.switchTo(clazz: Class<*>, withBegin: Boolean = false) {
    val int = Intent(this, clazz)

    int.flags = if (withBegin) {
        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    } else {
        Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    startActivity(int)
}

fun AppCompatActivity.root(): ViewGroup = findViewById(android.R.id.content)


fun Context.color(resId: Int) = ContextCompat.getColor(this, resId)
fun Fragment.color(resId: Int) = ContextCompat.getColor(activity, resId)

fun Context.str(resId: Int) = getString(resId)
fun Fragment.str(resId: Int) = getString(resId)
