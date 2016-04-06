package clean.news.flow

import android.app.Activity
import android.content.Context
import android.transition.AutoTransition
import android.transition.Scene
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.ViewGroup
import clean.news.R
import flow.Direction
import flow.KeyChanger
import flow.TraversalCallback

class SceneDispatcher(private val activity: Activity) : KeyChanger() {
	override fun changeKey(
			outgoingState: flow.State?,
			incomingState: flow.State,
			direction: Direction,
			incomingContexts: MutableMap<Any, Context>,
			callback: TraversalCallback) {

		val origin = outgoingState?.getKey<Any>()
		val destination = incomingState.getKey<WithLayout>()
		val layout = destination.getLayoutResId()
		val context = incomingContexts[destination]
		val frame = activity.findViewById(R.id.app_container) as ViewGroup
		val incomingView = LayoutInflater.from(context).inflate(layout, frame, false)

		outgoingState?.save(frame.getChildAt(0))

		val scene = Scene.getSceneForLayout(frame, layout, context)
		val transition = if (destination is WithTransition) {
			destination.createTransition(outgoingState?.getKey(), incomingState.getKey(), direction)
		}
		else if (origin is WithTransition) {
			origin.createTransition(outgoingState?.getKey(), incomingState.getKey(), direction)
		}
		else if (outgoingState != null) {
			AutoTransition()
		}
		else {
			null
		}

		TransitionManager.go(scene, transition)
		incomingState.restore(incomingView)

		callback.onTraversalCompleted()
	}
}
