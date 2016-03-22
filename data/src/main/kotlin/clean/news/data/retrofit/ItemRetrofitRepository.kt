package clean.news.data.retrofit

import clean.news.entity.Item
import clean.news.repository.item.ItemNetworkRepository
import clean.news.data.retrofit.service.ItemService
import rx.Observable
import javax.inject.Inject

class ItemRetrofitRepository @Inject constructor(
		private val itemService: ItemService) : ItemNetworkRepository {

	private val BUFFER = 20

	override fun getTopStories(): Observable<List<Item>> {
		return streamItems(itemService.getTopStories())
	}

	override fun getNewStories(): Observable<List<Item>> {
		return streamItems(itemService.getNewStories())
	}

	override fun getAskStories(): Observable<List<Item>> {
		return streamItems(itemService.getAskStories())
	}

	override fun getShowStories(): Observable<List<Item>> {
		return streamItems(itemService.getShowStories())
	}

	override fun getJobStories(): Observable<List<Item>> {
		return streamItems(itemService.getJobStories())
	}

	override fun getAll(): Observable<List<Item>> {
		throw UnsupportedOperationException("Cannot get all items from network.")
	}

	override fun getById(id: Long): Observable<Item> {
		return itemService.getById(id)
	}

	override fun save(item: Item): Observable<Boolean> {
		throw UnsupportedOperationException("Cannot save items to network.")
	}

	private fun streamItems(itemIdResponse: Observable<List<Long>>): Observable<List<Item>> {
		return itemIdResponse
				.flatMapIterable { it }
				.flatMap { itemService.getById(it) }
				.buffer(BUFFER)
				.scan { prev: List<Item>, next: List<Item> -> prev + next }
	}
}
