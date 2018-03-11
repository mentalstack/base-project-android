package com.mentalstack.baseproject.utils.nativeData

/**
 * Created by aleksandrovdenis on 03.03.2018.
 */

/**
 * Find first result of block method
 */
fun <T, V> Iterable<T>.firstOf(block: (T) -> V?): V? {
    forEach {
        block(it)?.let { return it }
    }
    return null
}

/**
 * Find first entry from block function
 */
fun <T, V> Map<T, V>.findFirst(func: (Map.Entry<T, V>) -> Boolean): Pair<T, V>? {
    forEach {
        if (func(it))
            return it.toPair()
    }
    return null
}

fun <T, V> Map<T, V>.findFirstValue(value: V): Pair<T, V>? = findFirst { it.value == value }
fun <T, V> Map<T, V>.findFirstKey(key: T): Pair<T, V>? = findFirst { it.key == key }

/**
 * Remove all elements from block function
 * @param block - check function
 * @return removed elements list
 */

fun <T> MutableCollection<T>.removeWithCond(block: (T) -> Boolean): MutableList<T> {
    val result = mutableListOf<T>()
    forEach {
        if (block.invoke(it)) {
            result.add(it)
        }
    }
    removeAll(result)

    return result
}

