package com.mentalstack.baseproject.utils.nativeData

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */

fun <T, V> Iterable<T>.firstOf(func: (T) -> V?): V? {
    forEach {
        func(it)?.let { return it }
    }
    return null
}

fun <T, V> Map<T, V>.findFirst(func: (Map.Entry<T, V>) -> Boolean): Pair<T, V>? {
    forEach {
        if (func(it))
            return it.toPair()
    }
    return null
}

fun <T, V> Map<T, V>.findFirstValue(value: V): Pair<T, V>? =
        filter { it.value == value }.toList().firstOrNull()

fun <T, V> Map<T, V>.findFirstKey(key: T): Pair<T, V>? =
        filter { it.key == key }.toList().firstOrNull()

fun <T> MutableCollection<T>.removeWithCond(func: (T) -> Boolean): MutableList<T> {
    val result = mutableListOf<T>()
    forEach {
        if (func.invoke(it)) {
            result.add(it)
        }
    }
    removeAll(result)

    return result
}

