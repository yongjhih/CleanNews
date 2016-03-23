package clean.news.app.usecase.item

import clean.news.app.usecase.RxUseCase
import clean.news.app.usecase.Strategy
import clean.news.core.entity.Item
import rx.Observable
import rx.schedulers.Schedulers


abstract class AbsGetItems(
		private val disk: Observable<List<Item>>,
		private val memory: Observable<List<Item>>,
		private val network: Observable<List<Item>>,
		private val saveItem: SaveItem) : RxUseCase<List<Item>> {

	override fun execute(flags: Int): Observable<List<Item>> {
		return Strategy(flags)
				.execute(disk, memory, network, save)
				.subscribeOn(Schedulers.io())
	}

	val save = { items: List<Item> ->
		items.forEach {
			saveItem.execute(it).subscribe()
		}
	}
}
