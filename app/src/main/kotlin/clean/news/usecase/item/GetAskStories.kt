package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.repository.item.ItemNetworkRepository
import clean.news.usecase.RxUseCase
import rx.Observable
import javax.inject.Inject

class GetAskStories @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository) : RxUseCase<List<Item>> {

	override fun execute(flags: Int): Observable<List<Item>> {
		return network.getAskStories()
	}
}
