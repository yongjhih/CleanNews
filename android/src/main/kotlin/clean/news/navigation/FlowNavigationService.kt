package clean.news.navigation

import android.content.Context
import clean.news.presentation.navigation.NavigationService
import flow.Direction
import flow.Flow
import flow.History
import java.lang.ref.WeakReference

class FlowNavigationService : NavigationService {
	private var contextRef = WeakReference<Context>(null)

	override fun goBack(): Boolean {
		return getFlow().goBack();
	}

	override fun goTo(newTop: Any) {
		getFlow().set(newTop)
	}

	override fun resetTo(newTop: Any) {
		getFlow().setHistory(History.single(newTop), Direction.REPLACE)
	}

	fun setContext(context: Context) {
		contextRef.clear()
		contextRef = WeakReference(context)
	}

	fun removeContext() {
		contextRef.clear()
	}

	fun getContext(): Context {
		return contextRef.get() ?: throw RuntimeException("Context is not set.")
	}

	protected fun getFlow(): Flow {
		return Flow.get(getContext())
	}
}
