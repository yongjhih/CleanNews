package clean.news.inject.module

import clean.news.navigation.AppNavigationService
import clean.news.navigation.FlowNavigationFactory
import clean.news.presentation.inject.ApplicationScope
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
	@Provides
	@ApplicationScope
	fun navigationService(): NavigationService {
		return AppNavigationService()
	}

	@Provides
	@ApplicationScope
	fun navigationFactory(): NavigationFactory {
		return FlowNavigationFactory()
	}
}
