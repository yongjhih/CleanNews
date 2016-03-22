package clean.news.inject.component

import clean.news.inject.module.MainActivityModule
import clean.news.ui.main.MainActivity
import dagger.Component

@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
	fun inject(main: MainActivity)
}
