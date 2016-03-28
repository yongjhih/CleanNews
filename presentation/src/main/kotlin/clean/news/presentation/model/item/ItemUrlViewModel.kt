package clean.news.presentation.model.item

import clean.news.core.entity.Item
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject

class ItemUrlViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		item: Item) {

	val backClicks = PublishSubject.create<Void>()
	val detailClicks = PublishSubject.create<Void>()

	val item = Observable.just(item)

	init {
		backClicks.subscribe { navService.goBack() }
		detailClicks.subscribe { navService.replaceTo(navFactory.itemDetail(item)) }
	}
}
