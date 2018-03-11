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
    /**
     * Read text file, return string
     * @param context
     * @param fileName - locale file path
     */
    fun readTextFromAssetsFile(context: Context, fileName: String): String? {
        return try {
            val some = context.assets.open(fileName) ?: return null
            val buffer = ByteArray(some.available())
            some.read(buffer)
            some.close()
            String(buffer)
        } catch (ex: IOException) {
            null
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

    /**
     * Check video size and duration
     * @param path - string path from video file
     * @param maxDuration - maximum time in seconds
     * @param maxMbSize - max size in megabytes
     */
    fun checkVideoFromUpload(path: String, maxDuration: Int?=null, maxMbSize: Int?=null): Boolean {
        return try {
            MediaMetadataRetriever().let {
                it.setDataSource(path)
                maxMbSize?.let { maxSize ->
                    val size = File(path).length() / 1048576
                    if (size <= maxSize)
                        return false
                }
               maxDuration?.let { maxLen ->
                   val duration: Int = it.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt() / 1000
                   return duration <= maxLen
               }

               return true
            }
        } catch (e: Exception) {
            false
        }
    }
}