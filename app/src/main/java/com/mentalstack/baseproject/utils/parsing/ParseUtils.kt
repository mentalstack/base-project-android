package com.mentalstack.baseproject.utils.parsing

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.safeCast

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */
fun <T : ParseData> parse(map: Map<String, Any>, key: String, type: KClass<T>, clear: Boolean = false): List<T> {
    return if (map.containsKey(key)) {
        val list = map[key] as? ArrayList<*>
        list?.filterIsInstance(Map::class.java)?.map {
            type.primaryConstructor!!.call(it).apply {
                if (clear)
                    clear()
            }
        } ?: arrayListOf()

    } else {
        arrayListOf()
    }
}

fun <T : Any> array(map: Map<String, Any>, key: String, type: KClass<T>): List<T> {
    return if (map.containsKey(key)) {
        val list = ArrayList::class.safeCast(map[key])
        list?.mapNotNull { type.safeCast(it) } ?: arrayListOf()
    } else {
        arrayListOf()
    }
}

fun <T : Any> Map<String, T>.subMap(key: String, default: Map<String, T>? = null): Map<String, T>? {
    return if (contains(key))
        this[key]?.let { it as? Map<String, T> } ?: default
    else
        default
}

fun <T : Any> Map<String, T>.subArray(key: String) =
        List::class.safeCast(get(key))?.mapNotNull { it as Map<String, T> }

fun <T : Any, V : Any> variable(map: Map<String, T>, key: String, clazz: KClass<V>, default: V? = null): V? {
    return clazz.safeCast(map[key]) ?: default
}

fun <T : Any, V : Any> value(map: Map<String, T>, key: String, clazz: KClass<V>, default: V? = null): V {
    return clazz.safeCast(map[key]) ?: default ?: throw Exception()
}

fun List<String>.stringify(): String = "[" + joinToString(",") { "\"$it\"" } + "]"

private val gson by lazy { GsonBuilder().setPrettyPrinting().create() }
fun parseString(str: String): Map<String, Any>? {
    return try {
        gson.fromJson(str, object : TypeToken<Map<String, Any>>() {}.type)
    } catch (e: Exception) {
        null
    }
}
