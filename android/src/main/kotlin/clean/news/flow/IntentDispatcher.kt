package clean.news.flow

import android.app.Activity
import android.content.Context
import flow.Direction
import flow.KeyChanger
import flow.State
import flow.TraversalCallback

class IntentDispatcher(private val activity: Activity) : KeyChanger() {
	override fun changeKey(
			outgoingState: State?,
			incomingState: State,
			direction: Direction,
			incomingContexts: MutableMap<Any, Context>,
			callback: TraversalCallback?) {
		if (outgoingState != null) {
			val destination = incomingState.getKey<WithIntent>()
			activity.startActivity(destination.intent())
		}
		callback?.onTraversalCompleted()
	}
}
