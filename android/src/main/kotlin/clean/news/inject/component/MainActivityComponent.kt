package clean.news.inject.component

import clean.news.inject.module.MainActivityModule
import clean.news.MainActivity
import dagger.Component

@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
	fun inject(mainActivity: MainActivity)
}
