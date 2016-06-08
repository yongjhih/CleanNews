package clean.news.presentation.model

import clean.news.presentation.collections.MutableStreamMap
import clean.news.presentation.collections.StreamMap
import clean.news.presentation.collections.mutableStreamMapOf
import clean.news.presentation.model.Model.Key
import rx.Observable
import rx.subjects.ReplaySubject
import rx.subjects.Subject
import rx.subscriptions.CompositeSubscription

abstract class Model<I : Key, O : Key> {

	private val proxyFactory = object : ProxyFactory<I> {
		override fun <T : Any> create(key: I): Subject<T, T> {
			return createProxy(key)
		}
	}

	private val proxy = SourcesProxy(proxyFactory)
	private val sinks: StreamMap<O> by lazy { bind(proxy) }

	/***
	 * Set up the binding between view input and output streams.
	 *
	 * @param sources Map of source streams.
	 * @return Map of sink streams.
	 */
	abstract protected fun bind(sources: StreamMap<I>): StreamMap<O>

	/***
	 * @param key Source stream key.
	 * @return A subject to which source streams will attach and detach.
	 */
	open protected fun <T : Any> createProxy(key: I): Subject<T, T> {
		return ReplaySubject.create(1)
	}

	/**
	 * Called when the model is attached to source streams.
	 */
	open protected fun onAttach() {
		// no op
	}

	/**
	 * Called when the model is detached from source streams.
	 */
	open protected fun onDetach() {
		// no op
	}

	/**
	 * Attach this model to source streams.
	 *
	 * @param sources Map of source streams.
	 * @return Map of sink streams.
	 */
	fun attach(sources: StreamMap<I>): StreamMap<O> {
		proxy.attach(sources)
		onAttach()
		return sinks
	}

	/**
	 * Detach this model from source streams.
	 */
	fun detach() {
		proxy.detach()
		onDetach()
	}

	/**
	 * Ensures independently typed keys for sources and sinks. Provides convenience method for getting stream.
	 */
	interface Key {

		/**
		 * Return a stream for this key.
		 *
		 * @param streamMap The map of streams which contains this key.
		 */
		operator fun <T : Any> invoke(streamMap: StreamMap<out Key>): Observable<T> {
			@Suppress("UNCHECKED_CAST")
			return (streamMap as StreamMap<Key>).stream<T>(this)
		}

	}

	// Proxy

	private interface ProxyFactory<K : Key> {
		fun <T : Any> create(key: K): Subject<T, T>
	}

	private class SourcesProxy<K : Key>(private val proxyFactory: ProxyFactory<K>) :
			MutableStreamMap<K> by mutableStreamMapOf<K>() {

		private val subscriptions = CompositeSubscription()

		override fun <T : Any> stream(key: K): Observable<T> {
			if (!containsKey(key)) {
				put(key, proxyFactory.create<T>(key))
			}
			@Suppress("UNCHECKED_CAST")
			return this[key] as Observable<T>
		}

		fun attach(sources: StreamMap<K>) {
			sources.keys.forEach { key ->
				@Suppress("UNCHECKED_CAST")
				sources.stream<Any>(key)
						.subscribe(stream<Any>(key) as Subject<Any, Any>)
						.apply { subscriptions.add(this) }
			}
		}

		fun detach() {
			subscriptions.clear()
		}

	}

}
