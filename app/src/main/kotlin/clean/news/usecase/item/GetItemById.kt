package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.repository.item.ItemNetworkRepository
import clean.news.usecase.RxUseCase1
import rx.Observable
import javax.inject.Inject

class GetItemById @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository) : RxUseCase1<Long, Item> {

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun execute(id: Long, flags: Int): Observable<Item> {
		return network.getById(id)
	}
}
