package clean.news.flow

import android.content.Context
import flow.Flow
import flow.Services.Binder
import flow.ServicesFactory

class ComponentService(private val rootComponent: Any) : ServicesFactory() {

	override fun bindServices(services: Binder) {
		val key = services.getKey<Any>() as WithComponent<Any>
		val parent = services.getService<Any>(NAME) ?: rootComponent
		val component = key.createComponent(parent)
		services.bind(NAME, component)
	}

	companion object {
		const val NAME = "ComponentService"

		fun <T> getService(context: Context) = Flow.getService<T>(NAME, context)
	}

}
