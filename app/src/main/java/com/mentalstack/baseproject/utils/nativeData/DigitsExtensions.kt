package com.mentalstack.baseproject.utils.nativeData

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */

/**
 * cut digits
 */
fun Double.format(digits: Int) =
        Math.pow(10.0, digits.toDouble()).toInt().let { pow -> Math.floor(this * pow) / pow }

fun percents(full: Float, current: Float): Float = ((full / current) * 100)
fun percents(full: Long, current: Long): Long = percents(full.toFloat(), current.toFloat()).toLong()
fun percents(full: Int, current: Int): Int = percents(full.toFloat(), current.toFloat()).toInt()
