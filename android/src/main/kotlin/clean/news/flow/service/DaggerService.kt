package clean.news.flow.service

import android.content.Context
import flow.Flow
import flow.Services.Binder
import flow.ServicesFactory

class DaggerService(private val rootComponent: Any) : ServicesFactory() {

	override fun bindServices(services: Binder) {
		val key = services.getKey<Any>()
		if (key !is WithComponent) {
			return
		}

		val parent = services.getService<Any>(NAME) ?: rootComponent
		val component = key.createComponent(parent)

		services.bind(NAME, component)
	}

	interface WithComponent {
		fun createComponent(parent: Any): Any
	}

	companion object {
		const val NAME = "DaggerService"

		fun <T> get(context: Context) = Flow.getService<T>(NAME, context)
	}

}
