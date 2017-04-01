package clean.news.presentation.model

import redux.api.Reducer
import redux.api.Store

abstract class StoreModel<S : Any> : Store<S> {

	private val store by lazy { createStore() }

	abstract fun createStore(): Store<S>

	override fun getState() = store.state

	override fun replaceReducer(reducer: Reducer<S>) = store.replaceReducer(reducer)

	override fun subscribe(subscriber: Store.Subscriber) = store.subscribe(subscriber)

	override fun dispatch(action: Any) = store.dispatch(action)
}
