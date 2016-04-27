package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetItemsByListType
import clean.news.app.usecase.item.GetItemsByListType.Request
import clean.news.app.util.addTo
import clean.news.core.entity.Item
import clean.news.presentation.model.Model
import clean.news.presentation.model.item.ItemListViewModel.Sinks
import clean.news.presentation.model.item.ItemListViewModel.Sources
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import rx.Observable
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val listType: Item.ListType,
		private val getItemsByListType: GetItemsByListType) : Model<Sources, Sinks> {
	
	private val subscriptions = CompositeSubscription()

	override fun setUp(sources: Sources): Sinks {
		sources.itemUrlClicks
				.subscribe() { navService.goTo(navFactory.url(it)) }
				.addTo(subscriptions)

		sources.itemDetailClicks
				.subscribe { navService.goTo(navFactory.itemDetail(it)) }
				.addTo(subscriptions)

		return Sinks(
				getItemsByListType.execute(Request(listType)).map { it.items }
		)
	}

	override fun tearDown() {
		subscriptions.clear()
	}

	class Sources(
			val itemUrlClicks: Observable<Item>,
			val itemDetailClicks: Observable<Item>) : Model.Sources

	class Sinks(val items: Observable<List<Item>>) : Model.Sinks
}
