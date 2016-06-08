package clean.news.presentation.collections

import rx.Observable
import java.util.LinkedHashMap

class HashStreamMap<K> : LinkedHashMap<K, Observable<*>>, MutableStreamMap<K> {

	constructor() : super()

	constructor(initialCapacity: Int) : super(initialCapacity)

	constructor(initialCapacity: Int, loadFactor: Float) : super(initialCapacity, loadFactor)

	constructor(m: MutableMap<out K, out Observable<*>>?) : super(m)

	constructor(initialCapacity: Int, loadFactor: Float, accessOrder: Boolean) : super(initialCapacity, loadFactor, accessOrder)

	override fun <T : Any> stream(key: K): Observable<T> {
		@Suppress("UNCHECKED_CAST")
		return this[key] as Observable<T>
	}

}
