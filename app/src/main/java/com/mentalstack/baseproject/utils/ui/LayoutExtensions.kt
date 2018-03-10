package com.mentalstack.baseproject.utils.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */
fun <DATA : Any> ViewGroup.add(layoutID: Int, list: List<DATA>, bindFunc: (View, DATA) -> Unit) {
    list.forEach { data ->
        LayoutInflater.from(context).inflate(layoutID, this, false).let { view ->
            addView(view)
            bindFunc.invoke(view, data)
        }
    }
}