package com.mentalstack.baseproject.utils.nativeData

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */
fun Double.format(digits: Int) =
        Math.pow(10.0, digits.toDouble()).toInt().let { pow -> Math.floor(this * pow) / pow }

fun percents(full: Long, current: Long): Int = ((full.toFloat() / current.toFloat()) * 100).toInt()