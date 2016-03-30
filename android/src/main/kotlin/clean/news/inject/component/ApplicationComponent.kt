package clean.news.inject.component

import android.app.Application
import clean.news.inject.module.ApplicationModule
import clean.news.presentation.inject.ApplicationScope
import clean.news.presentation.navigation.NavigationService
import clean.news.ui.main.MainScreen.MainComponent
import dagger.Component

@ApplicationScope
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
	fun application(): Application

	fun mainComponent(): MainComponent

	fun navigationService(): NavigationService
}
