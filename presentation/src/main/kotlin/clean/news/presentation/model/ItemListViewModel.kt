package clean.news.presentation.model

import clean.news.app.usecase.item.GetTopStories
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
		private val navigationService: NavigationService,
		private val navigationFactory: NavigationFactory,
		private val getTopStories: GetTopStories) {

	val items = getTopStories.execute()
}
