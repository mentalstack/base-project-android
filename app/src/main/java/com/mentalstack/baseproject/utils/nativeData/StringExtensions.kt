package com.mentalstack.baseproject.utils.nativeData

import android.os.Build
import android.text.Html

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */
enum class Regulars(val reg: Regex) {
    mail(Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")),
    password(Regex("^(?=.*[a-zA-Z0-9]).{6,40}\$")),
    notEmpty(Regex("^(?!\\s*$).+"))
}

fun String.isMail() = Regulars.mail.reg.matches(this.toLowerCase())
fun String.isDefaultPassword(): Boolean = Regulars.password.reg.matches(this)

fun String.asHtmlText() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, 0)
} else {
    Html.fromHtml(this)
}