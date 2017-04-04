package clean.news.data.retrofit

import clean.news.app.data.item.ItemNetworkDataSource
import clean.news.core.entity.Item
import clean.news.core.entity.Item.ListType
import clean.news.data.retrofit.service.ItemService
import io.reactivex.Observable
import javax.inject.Inject

class ItemRetrofitDataSource @Inject constructor(
		private val itemService: ItemService) : ItemNetworkDataSource {
	private val BUFFER = 5

	override fun getItems(listType: ListType): Observable<List<Item>> {
		val items = when (listType) {
			Item.ListType.TOP -> itemService.getTopStories().toObservable()
			Item.ListType.NEW -> itemService.getNewStories().toObservable()
			Item.ListType.SHOW -> itemService.getShowStories().toObservable()
			Item.ListType.ASK -> itemService.getAskStories().toObservable()
			Item.ListType.JOB -> itemService.getJobStories().toObservable()
		}
		return streamItems(items)
	}

	override fun getChildren(item: Item): Observable<List<Item>> {
		return getChildrenObservable(item)
				.buffer(BUFFER)
				.scan { prev: List<Item>, next: List<Item> -> prev + next }
	}

	override fun getAll(): Observable<List<Item>> {
		throw UnsupportedOperationException("Cannot get all items from the network.")
	}

	override fun get(key: Long): Observable<Item> {
		return itemService.getById(key).toObservable()
	}

	override fun put(key: Long, value: Item): Observable<Item> {
		throw UnsupportedOperationException("Cannot put an item to the network.")
	}

	override fun remove(key: Long): Observable<Item> {
		throw UnsupportedOperationException("Cannot remove an item from the network.")
	}

	// Private functions

	private fun getChildrenObservable(item: Item, level: Int = 0): Observable<Item> {
		val kids = item.kids
		if (kids == null || kids.isEmpty()) {
			return Observable.just(item)
		}

		val childObservable = Observable.fromIterable(item.kids.orEmpty())
				.concatMapEager { get(it) }
				.map { it.copy(level = level) }
				.filter { it.deleted != true }
				.concatMapEager { getChildrenObservable(it, level + 1) }

		return Observable.just(item)
				.concatWith(childObservable)
	}

	private fun streamItems(itemIdResponse: Observable<List<Long>>): Observable<List<Item>> {
		return itemIdResponse
				.flatMapIterable { it }
				.concatMapEager { itemService.getById(it).toObservable() }
				.buffer(BUFFER)
				.scan { prev: List<Item>, next: List<Item> -> prev + next }
	}
}
