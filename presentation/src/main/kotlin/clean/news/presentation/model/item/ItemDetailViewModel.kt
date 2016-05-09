package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetChildren
import clean.news.app.usecase.item.GetChildren.Request
import clean.news.app.util.addTo
import clean.news.core.entity.Item
import clean.news.presentation.inject.ScreenScope
import clean.news.presentation.model.Model
import clean.news.presentation.model.item.ItemDetailViewModel.Sinks
import clean.news.presentation.model.item.ItemDetailViewModel.Sources
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationFactory.ItemDetailScreen
import clean.news.presentation.navigation.NavigationService
import rx.Observable
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

@ScreenScope(ItemDetailScreen::class)
class ItemDetailViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val getChildren: GetChildren,
		private val item: Item) : Model<Sources, Sinks> {

	private val subscriptions = CompositeSubscription()

	private val children = getChildren.execute(Request(item))
			.map { it.items }
			.replay(1)
			.autoConnect()

	override fun setUp(sources: Sources): Sinks {
		sources.backClicks.subscribe { navService.goBack() }.addTo(subscriptions)
		sources.urlClicks.subscribe { navService.replaceTo(navFactory.url(item)) }.addTo(subscriptions)
		sources.shareClicks.subscribe { navService.goTo(navFactory.shareDetail(item)) }.addTo(subscriptions)

		return Sinks(
				Observable.just(item),
				children
		)
	}

	override fun tearDown() {
		subscriptions.clear()
	}

	class Sources(
			val backClicks: Observable<out Any>,
			val urlClicks: Observable<out Any>,
			val shareClicks: Observable<out Any>) : Model.Sources

	class Sinks(
			val item: Observable<Item>,
			val children: Observable<List<Item>>) : Model.Sinks
}
