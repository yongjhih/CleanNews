package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.repository.item.ItemNetworkRepository
import clean.news.usecase.RxUseCase1
import clean.news.usecase.Strategy
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
