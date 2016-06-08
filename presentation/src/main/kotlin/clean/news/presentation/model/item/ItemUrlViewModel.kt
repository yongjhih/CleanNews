package clean.news.presentation.model.item

import clean.news.core.entity.Item
import clean.news.presentation.collections.StreamMap
import clean.news.presentation.collections.streamMapOf
import clean.news.presentation.model.Model
import clean.news.presentation.model.item.ItemUrlViewModel.Sinks
import clean.news.presentation.model.item.ItemUrlViewModel.Sources
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import rx.Observable
import javax.inject.Inject

class ItemUrlViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val item: Item) : Model<Sources, Sinks>() {

	override fun bind(sources: StreamMap<Sources>): StreamMap<Sinks> {
		Sources.BACK_CLICKS<Unit>(sources)
				.subscribe { navService.goBack() }

		Sources.DETAIL_CLICKS<Unit>(sources)
				.subscribe { navService.replaceTo(navFactory.itemDetail(item)) }

		Sources.SHARE_CLICKS<Unit>(sources)
				.subscribe { navService.goTo(navFactory.shareUrl(item)) }

		return streamMapOf(
				Sinks.ITEM to Observable.just(item)
						.replay(1)
						.autoConnect()
		)
	}

	enum class Sources : Key {
		BACK_CLICKS, DETAIL_CLICKS, SHARE_CLICKS
	}

	enum class Sinks : Key {
		ITEM
	}
}
