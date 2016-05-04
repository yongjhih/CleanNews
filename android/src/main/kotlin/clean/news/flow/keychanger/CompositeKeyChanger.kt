package clean.news.flow.keychanger

import android.content.Context
import flow.Direction
import flow.KeyChanger
import flow.TraversalCallback
import kotlin.reflect.KClass

class CompositeKeyChanger : KeyChanger() {

	private val dispatchers = mutableMapOf<KClass<out Any>, KeyChanger>()

	fun addDispatcher(cls: KClass<out Any>, dispatcher: KeyChanger) {
		dispatchers.put(cls, dispatcher)
	}

	override fun changeKey(
			outgoingState: flow.State?,
			incomingState: flow.State,
			direction: Direction,
			incomingContexts: MutableMap<Any, Context>,
			callback: TraversalCallback) {

		val destination = incomingState.getKey<Any>()

		for ((cls, dispatcher) in dispatchers) {
			if (cls.java.isAssignableFrom(destination.javaClass)) {
				dispatcher.changeKey(outgoingState, incomingState, direction, incomingContexts, callback)
			}
		}
	}

}
