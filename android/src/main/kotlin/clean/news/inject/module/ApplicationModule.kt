package clean.news.inject.module

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {
	@Provides
	fun application(): Application {
		return application
	}
}
