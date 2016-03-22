package clean.news.model

import clean.news.navigation.NavigationFactory
import clean.news.navigation.NavigationService
import clean.news.usecase.item.GetTopStories
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
		private val navigationService: NavigationService,
		private val navigationFactory: NavigationFactory,
		private val getTopStories: GetTopStories) {

	val items = getTopStories.execute()
}
