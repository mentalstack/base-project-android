package com.mentalstack.baseproject.utils.files

import android.content.Context
import android.media.MediaMetadataRetriever
import java.io.File
import java.io.IOException

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */

fun File.videoDuration() = FileUtils.videoDuration(path)

object FileUtils {

    fun readTextFromAssetsFile(context: Context, fileName: String): String {
        return try {
            val some = context.assets.open(fileName) ?: return ""
            val buffer = ByteArray(some.available())
            some.read(buffer)
            some.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            ""
        }
    }

    fun videoDuration(path: String): Int? {
        return try {
            MediaMetadataRetriever().let {
                it.setDataSource(path)
                it.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt()
            }
        } catch (e: Exception) {
            null
        }
    }

    fun checkVideoFromUpload(path: String, maxDuration: Int = Int.MAX_VALUE, maxMbSize: Int = Int.MAX_VALUE): Boolean {
        return try {
            MediaMetadataRetriever().let {
                it.setDataSource(path)
                val size = File(path).length() / 1048576
                if (size <= maxMbSize)
                    return false

                val duration: Int = it.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt() / 1000
                return duration <= maxDuration
            }
        } catch (e: Exception) {
            false
        }
    }
}