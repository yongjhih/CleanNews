package clean.news.flow

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
	}

}
