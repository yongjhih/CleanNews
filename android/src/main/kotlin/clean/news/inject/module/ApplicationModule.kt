package clean.news.inject.module

import android.app.Application
import clean.news.app.util.Logger
import clean.news.presentation.inject.ApplicationScope
import clean.news.util.AndroidLogger
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {
	@Provides
	@ApplicationScope
	fun application(): Application {
		return application
	}

	@Provides
	@ApplicationScope
	fun logger(): Logger {
		return AndroidLogger()
	}
}
