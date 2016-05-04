package clean.news.flow.service

import android.content.Context
import flow.Flow
import flow.Services
import flow.Services.Binder
import flow.ServicesFactory
import mortar.MortarScope

class MortarService(private val rootScope: MortarScope) : ServicesFactory() {

	override fun bindServices(services: Binder) {
		val key = services.getKey<Any>()
		if (key !is WithScope) {
			return
		}

		val scopeName = key.javaClass.name
		val parentScope = services.getService<MortarScope>(NAME) ?: rootScope
		val childScope = parentScope.findChild(scopeName) ?: key.createScope(parentScope).build(scopeName)

		services.bind(NAME, childScope)
	}

	override fun tearDownServices(services: Services) {
		services.getService<MortarScope>(NAME)?.destroy()
		super.tearDownServices(services)
	}

	interface WithScope {
		fun createScope(parentScope: MortarScope): MortarScope.Builder
	}

	companion object {
		const val NAME = "MortarService"

		fun <T> get(context: Context) = Flow.getService<T>(NAME, context)
	}

}
