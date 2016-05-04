package clean.news

import android.app.Application
import android.content.Context
import clean.news.flow.service.DaggerService
import clean.news.inject.component.ApplicationComponent
import clean.news.inject.component.DaggerApplicationComponent
import clean.news.inject.module.ApplicationModule
import clean.news.inject.module.DataModule
import clean.news.inject.module.PresentationModule
import mortar.MortarScope

class CleanNewsApplication : Application() {
	private lateinit var applicationComponent: ApplicationComponent
	private lateinit var rootScope: MortarScope

	override fun onCreate() {
		super.onCreate()

		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(ApplicationModule(this))
				.presentationModule(PresentationModule())
				.dataModule(DataModule())
				.build()

		rootScope = MortarScope.buildRootScope()
				.withService(DaggerService.NAME, applicationComponent)
				.build("ROOT")
	}

	fun component(): ApplicationComponent {
		return applicationComponent
	}

	fun scope(): MortarScope {
		return rootScope
	}

	companion object {
		fun get(context: Context) = context.applicationContext as CleanNewsApplication
	}
}
