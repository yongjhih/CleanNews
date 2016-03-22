package clean.news.inject.component

import android.app.Application
import clean.news.inject.module.ApplicationModule
import dagger.Component

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
	fun application(): Application
}
