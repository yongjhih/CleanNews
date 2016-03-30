package clean.news.app.usecase.item

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.app.repository.item.ItemMemoryRepository
import clean.news.app.repository.item.ItemNetworkRepository
import clean.news.app.usecase.RxUseCase1
import clean.news.app.usecase.Strategy
import clean.news.core.entity.Item
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject

class GetComments @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository,
		private val saveItem: SaveItem) : RxUseCase1<Item, List<Item>> {

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun execute(item: Item, flags: Int): Observable<List<Item>> {
		return Strategy(flags)
				.execute(disk.getComments(item), memory.getComments(item), network.getComments(item), save)
				.subscribeOn(Schedulers.io())
	}

	val save = { items: List<Item> ->
		items.forEach {
			saveItem.execute(it).subscribe()
		}
	}
}
