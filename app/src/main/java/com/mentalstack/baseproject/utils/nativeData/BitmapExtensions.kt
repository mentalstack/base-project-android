package com.mentalstack.baseproject.utils.nativeData

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import org.jetbrains.anko.doAsync
import java.io.File
import java.io.FileOutputStream

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */


fun Bitmap.scaleTo(size: Pair<Int, Int>) = scaleTo( size.first, size.second)
fun Bitmap.scaleTo(w:Int,h:Int):Bitmap = Bitmap.createScaledBitmap( this, w, h, false)

/**
 * async converting method
 */
fun Bitmap.convertToFile(name: String, scaledSize: Pair<Int, Int>? = null, onSuccess: (File?) -> Unit) {
    doAsync {
        try {
            val result = scaledSize?.let { scaleTo(it) } ?: this@convertToFile
            val file = File.createTempFile(name, ".png")
            val fOut = FileOutputStream(file)
            result.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            fOut.close()
            onSuccess(file)
        } catch (e: Exception) {
            onSuccess(null)
        }
    }
}

/**
 * async converting method
 */
fun Drawable.toBitmap( onSuccess: (Bitmap?) -> Unit) {
    doAsync {
        if(this@toBitmap is BitmapDrawable){
            (this@toBitmap as? BitmapDrawable)?.let {
                onSuccess(it.bitmap)
            }
        } else {
            val bitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            } else {
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(bitmap)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)
            onSuccess(bitmap)
        }
    }
}