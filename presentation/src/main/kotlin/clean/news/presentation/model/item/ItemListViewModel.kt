package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetAskStories
import clean.news.app.usecase.item.GetJobStories
import clean.news.app.usecase.item.GetNewStories
import clean.news.app.usecase.item.GetShowStories
import clean.news.app.usecase.item.GetTopStories
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
		private val getTopStories: GetTopStories,
		private val getNewStories: GetNewStories,
		private val getShowStories: GetShowStories,
		private val getAskStories: GetAskStories,
		private val getJobStories: GetJobStories) : Model<Sources, Sinks> {


	private val subscriptions = CompositeSubscription()

	override fun onAttach(sources: Sources): Sinks {
		sources.itemUrlClicks
				.subscribe() { navService.goTo(navFactory.url(it)) }
				.addTo(subscriptions)

		sources.itemDetailClicks
				.subscribe { navService.goTo(navFactory.itemDetail(it)) }
				.addTo(subscriptions)

		val items = when (listType) {
			Item.ListType.TOP -> getTopStories.execute()
			Item.ListType.NEW -> getNewStories.execute()
			Item.ListType.SHOW -> getShowStories.execute()
			Item.ListType.ASK -> getAskStories.execute()
			Item.ListType.JOBS -> getJobStories.execute()
		}

		return Sinks(items)
	}

	override fun onDetach() {
		subscriptions.clear()
	}

	class Sources(
			val itemUrlClicks: Observable<Item>,
			val itemDetailClicks: Observable<Item>) : Model.Sources

	class Sinks(val items: Observable<List<Item>>) : Model.Sinks
}
