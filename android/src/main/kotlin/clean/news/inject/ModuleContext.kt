package clean.news.inject

import android.content.Context
import android.content.ContextWrapper

class ModuleContext<T : Any>(context: Context, val module: T) : ContextWrapper(context) {
	companion object {
		inline fun <reified T : Any> get(context: Context): T {
			var currentContext = context
			while (currentContext is ContextWrapper) {
				if (currentContext is ModuleContext<*> && currentContext.module is T) {
					return currentContext.module as T
				}
				currentContext = currentContext.baseContext
			}
			throw IllegalArgumentException("Context hierarchy contains no module of type ${T::class.java}")
		}
	}
}
