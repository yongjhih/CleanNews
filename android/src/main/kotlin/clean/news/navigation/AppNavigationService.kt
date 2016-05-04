package clean.news.navigation

import android.content.Intent

class AppNavigationService : FlowNavigationService() {

	override fun goTo(newTop: Any) {
		navigate(newTop) { super.goTo(newTop) }
	}

	override fun replaceTo(newTop: Any) {
		navigate(newTop) { super.replaceTo(newTop) }
	}

	override fun resetTo(newTop: Any) {
		navigate(newTop) { super.resetTo(newTop) }
	}

	private fun navigate(newTop: Any, default: () -> Any?) {
		when (newTop) {
			is WithActivity -> getContext().startActivity(newTop.createIntent())
			else -> default()
		}
	}

	interface WithActivity {
		fun createIntent(): Intent
	}

}
