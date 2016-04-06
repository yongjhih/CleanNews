package clean.news.flow

import android.content.Context
import flow.Direction
import flow.KeyChanger
import flow.TraversalCallback
import kotlin.reflect.KClass

class CompositeDispatcher : KeyChanger() {
	private val dispatchers = mutableMapOf<KClass<*>, KeyChanger>()

	fun addDispatcher(clz: KClass<*>, dispatcher: KeyChanger) {
		dispatchers.put(clz, dispatcher)
	}

	override fun changeKey(
			outgoingState: flow.State?,
			incomingState: flow.State,
			direction: Direction,
			incomingContexts: MutableMap<Any, Context>,
			callback: TraversalCallback) {

		val destination: Any = incomingState.getKey()

		for ((cls, dispatcher) in dispatchers) {
			if (cls.java.isAssignableFrom(destination.javaClass)) {
				dispatcher.changeKey(
						outgoingState,
						incomingState,
						direction,
						incomingContexts,
						callback)
				return
			}
		}
	}
}
