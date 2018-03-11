package com.mentalstack.baseproject.utils.parsing

import com.mentalstack.baseproject.utils.nativeData.firstOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */

internal val _emptyObj = mapOf<String, Any>("empty" to true)

/**
 * class [ParseData] from fast and simple parsing objects.
 * @param raw - raw map of values.
 */
open class ParseData(protected var raw: Map<String, Any>) {
    protected fun str(key: String, default: String? = ""): String {
        return value(raw, key, String::class, default)
    }

    /**
     * remove raw object. If you see { "empty" : true }, method [clear] already called
     */
    fun clear() {
        raw = _emptyObj
    }

    protected fun date(key: String, format: SimpleDateFormat): Calendar? {
        return try {
            val calendar = Calendar.getInstance()
            calendar.time = format.parse(key)
            calendar
        } catch (e: Exception) {
            null
        }
    }

    /**
     * return first of finded values.
     * If the backend can return the identifier for different keys (id, _id), call
     * val id = strFrom(["id", "_id"])
     */
    fun strFrom(keys: List<String>, default: String? = ""): String {
        return keys.firstOf { variable(raw, it, String::class) } ?: default
        ?: throw Exception("element not found")
    }

    protected fun int(key: String, default: Int = 0): Int {
        return variable(raw, key, Int::class) ?: variable(raw, key, Double::class)?.toInt()
        ?: variable(raw, key, Number::class)?.toInt() ?: default
    }

    protected fun strArray(key: String): List<String> = array(raw, key, String::class)

    /**
     * parse data from raw in extends ParseData instance.
     * @param key - key
     * @param clazz - child class
     * @param clear - call [clear] method in result, if true
     */
    fun <T : ParseData> instanceOf(key: String, clazz: KClass<T>, clear: Boolean = false): T? {
        return if (raw.contains(key)) {
            try {
                val data = raw[key] as? Map<*, *>
                data?.let {
                    clazz.primaryConstructor?.call(data).apply {
                        if (clear)
                            clear()
                    }
                }
            } catch (e: Exception) {
                null
            }
        } else
            null
    }


    /**
     * parse array from raw in extends ParseData instances.
     * @param key - key
     * @param clazz - child class
     * @param clear - call [clear] method in result, if true
     */
    fun <T : ParseData> parse(key: String, type: KClass<T>, clear: Boolean = true): List<T> =
            parse(raw, key, type).apply { if (clear) forEach { it.clear() } }
}