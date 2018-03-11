package com.mentalstack.baseproject.utils.nativeData

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */

private object PATTERNS {
    val MON by lazy { SimpleDateFormat("MMM", Locale.ENGLISH) }
    val year by lazy { SimpleDateFormat("yyyy", Locale.ENGLISH) }
    val camera by lazy { SimpleDateFormat("mm:ss", Locale.ENGLISH) }
}

fun Calendar.secondsToThis(): Int {
    val dateInSeconds = timeInMillis.let { it + timeZone.getOffset(it) }
    return ((System.currentTimeMillis() - dateInSeconds) / 1000).toInt()
}

fun Long.cameraTime(): String = "  ${PATTERNS.camera.format(Date(this))}  "
fun Int.cameraTime(): String = this.toLong().cameraTime()

fun Calendar.seconds() = timeInMillis.let { it + timeZone.getOffset(it) } / 1000

fun Calendar.month() = PATTERNS.MON.format(time)!!
fun Calendar.year() = PATTERNS.year.format(time)!!
fun Calendar.week() = intWeek().toString()
fun Calendar.day() = intDay().toString()

fun Calendar.intWeek() = get(Calendar.DAY_OF_MONTH) / 7 + 1
fun Calendar.intMonth() = get(Calendar.MONTH) + 1
fun Calendar.intYear() = get(Calendar.YEAR)
fun Calendar.intDay() = get(Calendar.DAY_OF_MONTH)

fun calendarFrom(year: Int = 0, month: Int = 0, date: Int = 0, hourOfDay: Int = 0, minute: Int = 0, second: Int = 0) =
        Calendar.getInstance().apply {
            set(year, month, date, hourOfDay, minute, second)
        }

fun Calendar.copy(): Calendar = clone() as Calendar

