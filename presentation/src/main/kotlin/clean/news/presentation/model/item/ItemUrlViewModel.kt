package clean.news.presentation.model.item

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

	override fun onAttach(sources: Sources): Sinks {
		sources.backClicks
				.subscribe { navService.goBack() }
				.apply { subscriptions.add(this) }

		sources.detailClicks
				.subscribe { navService.replaceTo(navFactory.itemDetail(item)) }
				.apply { subscriptions.add(this) }

		return Sinks(
				Observable.just(item)
						.replay(1)
						.autoConnect()
		)
	}

	override fun onDetach() {
		subscriptions.clear()
	}

	// Sources
	class Sources(
			val backClicks: Observable<Unit>,
			val detailClicks: Observable<Unit>) : Model.Sources

	// Sinks
	class Sinks(val item: Observable<Item>) : Model.Sinks
}
