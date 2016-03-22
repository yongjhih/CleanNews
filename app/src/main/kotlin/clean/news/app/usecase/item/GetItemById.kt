package clean.news.app.usecase.item

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.app.repository.item.ItemMemoryRepository
import clean.news.app.repository.item.ItemNetworkRepository
import clean.news.app.usecase.RxUseCase1
import clean.news.app.usecase.Strategy
import clean.news.core.entity.Item
import rx.Observable
import javax.inject.Inject

class GetItemById @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository,
		private val saveItem: SaveItem) : RxUseCase1<Long, Item> {

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun execute(id: Long, flags: Int): Observable<Item> {
		val strategy = Strategy(flags)

		return strategy.execute(
				disk.getById(id),
				memory.getById(id),
				network.getById(id),
				save)
	}

	private val save = { item: Item ->
		saveItem.execute(item).subscribe()
	}
}
