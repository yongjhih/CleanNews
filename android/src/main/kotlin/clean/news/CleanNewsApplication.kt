package clean.news

import android.app.Application
import clean.news.inject.component.ApplicationComponent
import clean.news.inject.component.DaggerApplicationComponent
import clean.news.inject.module.ApplicationModule

class CleanNewsApplication : Application() {
	private lateinit var applicationComponent: ApplicationComponent

	override fun onCreate() {
		super.onCreate()
		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(ApplicationModule(this))
				.build()
	}

	fun component(): ApplicationComponent {
		return applicationComponent
	}
}
