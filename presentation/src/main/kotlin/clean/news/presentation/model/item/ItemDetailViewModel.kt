package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetChildren
import clean.news.app.usecase.item.GetChildren.Request
import clean.news.core.entity.Item
import clean.news.presentation.collections.StreamMap
import clean.news.presentation.collections.streamMapOf
import clean.news.presentation.inject.ScreenScope
import clean.news.presentation.model.Model
import clean.news.presentation.model.item.ItemDetailViewModel.Sinks
import clean.news.presentation.model.item.ItemDetailViewModel.Sources
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationFactory.ItemDetailScreen
import clean.news.presentation.navigation.NavigationService
import rx.Observable
import javax.inject.Inject

@ScreenScope(ItemDetailScreen::class)
class ItemDetailViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val getChildren: GetChildren,
		private val item: Item) : Model<Sources, Sinks>() {

	private val children = getChildren.execute(Request(item))
			.map { it.items }
			.replay(1)
			.autoConnect()

	override fun bind(sources: StreamMap<Sources>): StreamMap<Sinks> {
		Sources.BACK_CLICKS<Unit>(sources)
				.subscribe { navService.goBack() }

		Sources.URL_CLICKS<Unit>(sources)
				.subscribe { navService.replaceTo(navFactory.url(item)) }

		Sources.SHARE_CLICKS<Unit>(sources)
				.subscribe { navService.goTo(navFactory.shareDetail(item)) }

		return streamMapOf(
				Sinks.ITEM to Observable.just(item),
				Sinks.CHILDREN to children
		)
	}

	enum class Sources : Key {
		BACK_CLICKS, URL_CLICKS, SHARE_CLICKS
	}

	enum class Sinks : Key {
		ITEM, CHILDREN
	}

}
