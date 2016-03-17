package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.repository.item.ItemNetworkRepository
import clean.news.usecase.RxUseCase
import rx.Observable
import javax.inject.Inject

class GetColdJobStories @Inject constructor(
		private val itemRepository: ItemNetworkRepository) : RxUseCase<List<Item>> {

	override fun execute(): Observable<List<Item>> {
		return itemRepository.getJobStories()
	}
}
