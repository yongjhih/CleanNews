package clean.news.presentation.model.item

import clean.news.app.util.addTo
import clean.news.core.entity.Item
import clean.news.presentation.model.Model
import clean.news.presentation.model.item.ItemUrlViewModel.Sinks
import clean.news.presentation.model.item.ItemUrlViewModel.Sources
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import rx.Observable
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemUrlViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val item: Item) : Model<Sources, Sinks> {

	private val subscriptions = CompositeSubscription()

	override fun setUp(sources: Sources): Sinks {
		sources.backClicks
				.subscribe { navService.goBack() }
				.addTo(subscriptions)

		sources.detailClicks
				.subscribe { navService.replaceTo(navFactory.itemDetail(item)) }
				.addTo(subscriptions)

		sources.shareClicks
				.subscribe { navService.goTo(navFactory.shareUrl(item)) }
				.addTo(subscriptions)

		return Sinks(
				Observable.just(item)
						.replay(1)
						.autoConnect()
		)
	}

	override fun tearDown() {
		subscriptions.clear()
	}

	class Sources(
			val backClicks: Observable<Unit>,
			val detailClicks: Observable<Unit>,
			val shareClicks: Observable<Unit>) : Model.Sources

	class Sinks(val item: Observable<Item>) : Model.Sinks
}
