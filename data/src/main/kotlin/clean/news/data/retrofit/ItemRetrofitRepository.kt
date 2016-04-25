package clean.news.data.retrofit

import clean.news.app.repository.item.ItemNetworkRepository
import clean.news.core.entity.Item
import clean.news.data.retrofit.service.ItemService
import rx.Observable
import javax.inject.Inject

class ItemRetrofitRepository @Inject constructor(
		private val itemService: ItemService) : ItemNetworkRepository {

	private val BUFFER = 5

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

	override fun getComments(item: Item): Observable<List<Item>> {
		return getCommentsObservable(item)
				.buffer(BUFFER)
				.scan { prev: List<Item>, next: List<Item> -> prev + next }
	}

	override fun getAll(): Observable<List<Item>> {
		throw UnsupportedOperationException("Cannot get all items from network.")
	}

	override fun getById(id: Long): Observable<Item> {
		return itemService.getById(id)
	}

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun save(item: Item): Observable<Boolean> {
		throw UnsupportedOperationException("Cannot save items to network.")
	}

	// Private functions

	private fun getCommentsObservable(item: Item, level: Int = 0): Observable<Item> {
		val kids = item.kids
		if (kids == null || kids.isEmpty()) {
			return Observable.just(item)
		}

		val childObservable = Observable.from(item.kids.orEmpty())
				.concatMapEager { getById(it) }
				.map { it.copy(level = level) }
				.filter { it.deleted != true }
				.concatMapEager { getCommentsObservable(it, level + 1) }

		return Observable.just(item)
				.concatWith(childObservable)
	}

	private fun streamItems(itemIdResponse: Observable<List<Long>>): Observable<List<Item>> {
		return itemIdResponse
				.flatMapIterable { it }
				.concatMapEager { itemService.getById(it) }
				.buffer(BUFFER)
				.scan { prev: List<Item>, next: List<Item> -> prev + next }
	}
}
