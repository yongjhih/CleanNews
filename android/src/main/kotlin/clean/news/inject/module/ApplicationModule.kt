package clean.news.inject.module

import android.app.Application
import clean.news.presentation.inject.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {
	@Provides
	@ApplicationScope
	fun application(): Application {
		return application
	}
}
