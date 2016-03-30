package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetComments
import clean.news.core.entity.Item
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject

class ItemDetailViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val getComments: GetComments,
		item: Item) {

	val backClicks = PublishSubject.create<Void>()
	val urlClicks = PublishSubject.create<Void>()

	val item = Observable.just(item)
	val comments = getComments.execute(item)
			.replay(1)
			.autoConnect()

	init {
		backClicks.subscribe { navService.goBack() }
		urlClicks.subscribe { navService.replaceTo(navFactory.url(item)) }
	}
}
