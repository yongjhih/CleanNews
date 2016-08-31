package clean.news

import android.app.Application
import android.content.Context
import clean.news.inject.component.ApplicationComponent
import clean.news.inject.component.DaggerApplicationComponent
import clean.news.inject.module.ApplicationModule
import clean.news.inject.module.DataModule
import clean.news.inject.module.PresentationModule

class CleanNewsApplication : Application() {
	private lateinit var applicationComponent: ApplicationComponent

	override fun onCreate() {
		super.onCreate()

		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(ApplicationModule(this))
				.presentationModule(PresentationModule())
				.dataModule(DataModule())
				.build()
	}

	fun component(): ApplicationComponent {
		return applicationComponent
	}

	companion object {
		fun get(context: Context) = context.applicationContext as CleanNewsApplication
	}
}
