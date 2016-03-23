package clean.news.inject.component

import android.app.Application
import clean.news.inject.module.ApplicationModule
import clean.news.inject.module.DataModule
import clean.news.inject.module.PresentationModule
import clean.news.presentation.inject.ApplicationScope
import clean.news.ui.main.MainScreen.MainComponent
import dagger.Component

@ApplicationScope
@Component(modules = arrayOf(ApplicationModule::class, PresentationModule::class, DataModule::class))
interface ApplicationComponent {
	fun application(): Application

	fun mainComponent(): MainComponent
}
