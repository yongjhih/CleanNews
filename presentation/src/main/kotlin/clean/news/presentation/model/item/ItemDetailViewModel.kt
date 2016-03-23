package clean.news.presentation.model.item

import clean.news.presentation.inject.ActivityScope
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import javax.inject.Inject

@ActivityScope
class ItemDetailViewModel @Inject constructor(
		private val navigationService: NavigationService,
		private val navigationFactory: NavigationFactory) {

}
