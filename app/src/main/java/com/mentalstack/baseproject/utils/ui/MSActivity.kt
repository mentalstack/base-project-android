package com.mentalstack.baseproject.utils.ui

import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import ru.alexbykov.nopermission.PermissionHelper

enum class PermState {
    OK, DENIED, NEVER_ASK
}

open class MSActivity : AppCompatActivity() {
    private var permissionHelper: PermissionHelper? = null
    private var observer: ViewTreeObserver? = null

    /**
     * Keyboard show listener
     * @property isShown true - keyboard now show
     */
    open fun onKeyboardChanged(isShown: Boolean) {
        //override me
    }

    /**
     *  Permission helper
     *  @property perms needed permissions
     *  @property answer callback with result argument - OK, DENIED or NEVER_ASK
     */
    fun checkPermissions(perms: Array<String>, answer: (PermState) -> Unit) {
        permissionHelper =
                PermissionHelper(this)
                        .check(*perms)
                        .onSuccess { answer(PermState.OK) }
                        .onDenied { answer(PermState.DENIED) }
                        .onNeverAskAgain { answer(PermState.NEVER_ASK) }
        permissionHelper?.run()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = root().viewTreeObserver
        observer?.addOnGlobalLayoutListener(globalLayoutListener)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    }

    override fun onDestroy() {
        if (observer?.isAlive == true)
            observer?.removeOnGlobalLayoutListener(globalLayoutListener)
        observer = null

        permissionHelper = null

        super.onDestroy()
    }

    private val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val root = root()
        val rect = Rect().apply { root.getWindowVisibleDisplayFrame(this) }
        val screenHeight = root.height
        val keypadHeight = screenHeight - rect.bottom
        val keyboardShowed = keypadHeight > screenHeight * 0.15
        onKeyboardChanged(keyboardShowed)
    }
}