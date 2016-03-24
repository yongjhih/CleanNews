package clean.news.flow

import android.transition.Transition
import flow.Direction

interface WithTransition {
	fun createTransition(fromKey: Any?, toKey: Any, direction: Direction): Transition
}
