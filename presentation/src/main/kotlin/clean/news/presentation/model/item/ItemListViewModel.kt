package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetTopStories
import clean.news.presentation.inject.ActivityScope
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import javax.inject.Inject

@ActivityScope
class ItemListViewModel @Inject constructor(
		private val navigationService: NavigationService,
		private val navigationFactory: NavigationFactory,
		private val getTopStories: GetTopStories) {

	val items = getTopStories.execute()
}
