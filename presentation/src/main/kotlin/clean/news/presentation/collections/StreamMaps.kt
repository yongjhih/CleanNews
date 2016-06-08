package clean.news.presentation.collections

import rx.Observable

// Maps

interface StreamMap<K> : Map<K, Observable<*>> {
	fun <T : Any> stream(key: K): Observable<T>
}

interface MutableStreamMap<K> : StreamMap<K>, MutableMap<K, Observable<*>>

// Utils

fun <K> streamMapOf(vararg pairs: Pair<K, Observable<*>>): StreamMap<K> = hashStreamMapOf(*pairs)

fun <K> mutableStreamMapOf(vararg pairs: Pair<K, Observable<*>>): MutableStreamMap<K> = hashStreamMapOf(*pairs)

fun <K> hashStreamMapOf(vararg pairs: Pair<K, Observable<*>>): MutableStreamMap<K>
		= HashStreamMap<K>(mapCapacity(pairs.size)).apply { putAll(pairs) }

// Taken from Maps.kt

private val INT_MAX_POWER_OF_TWO: Int = Int.MAX_VALUE / 2 + 1

private fun mapCapacity(expectedSize: Int): Int {
	if (expectedSize < 3) {
		return expectedSize + 1
	}
	if (expectedSize < INT_MAX_POWER_OF_TWO) {
		return expectedSize + expectedSize / 3
	}
	return Int.MAX_VALUE // any large value
}
