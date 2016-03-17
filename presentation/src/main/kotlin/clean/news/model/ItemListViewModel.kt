package clean.news.model

import clean.news.usecase.item.GetTopStories
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
		private val getTopStories: GetTopStories) {

	val items = getTopStories.execute()
}
