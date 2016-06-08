package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetItemsByListType
import clean.news.app.usecase.item.GetItemsByListType.Request
import clean.news.core.entity.Item
import clean.news.presentation.collections.StreamMap
import clean.news.presentation.collections.streamMapOf
import clean.news.presentation.model.Model
import clean.news.presentation.model.item.ItemListViewModel.Sinks
import clean.news.presentation.model.item.ItemListViewModel.Sinks.ITEMS
import clean.news.presentation.model.item.ItemListViewModel.Sources
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val listType: Item.ListType,
		private val getItemsByListType: GetItemsByListType) : Model<Sources, Sinks>() {

	override fun bind(sources: StreamMap<Sources>): StreamMap<Sinks> {
		Sources.ITEM_URL_CLICKS<Item>(sources)
				.subscribe { navService.goTo(navFactory.url(it)) }

		Sources.ITEM_DETAIL_CLICKS<Item>(sources)
				.subscribe { navService.goTo(navFactory.itemDetail(it)) }

		return streamMapOf(
				ITEMS to getItemsByListType.execute(Request(listType)).map { it.items }
		)
	}

	enum class Sources : Key {
		ITEM_URL_CLICKS, ITEM_DETAIL_CLICKS
	}

	enum class Sinks : Key {
		ITEMS
	}
}
