package clean.news.model

import clean.news.usecase.item.GetColdTopStories
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
		private val getColdTopStories: GetColdTopStories) {

	val items = getColdTopStories.execute()
}
