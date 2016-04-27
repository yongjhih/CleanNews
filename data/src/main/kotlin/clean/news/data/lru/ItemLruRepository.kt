package clean.news.data.lru

import android.util.LruCache
import clean.news.app.repository.item.ItemMemoryRepository
import clean.news.core.entity.Item
import clean.news.core.entity.Item.ListType
import clean.news.core.entity.Item.Type
import rx.Observable

class ItemLruRepository : ItemMemoryRepository {
	private val BUFFER = 10

	private val lru = LruCache<Long, Item>(512)

	override fun getItems(listType: ListType): Observable<List<Item>> {
		return when (listType) {
			Item.ListType.JOB -> Observable.just(lru.snapshot().values.filter { it.type == Type.JOB })
			else -> Observable.empty()
		}
	}

	override fun getChildren(item: Item): Observable<List<Item>> {
		return getChildrenObservable(item, 0)
				.buffer(BUFFER)
				.scan { prev: List<Item>, next: List<Item> -> prev + next }
	}

	override fun getAll(): Observable<List<Item>> {
		return Observable.just(lru.snapshot().values.toList())
	}

	override fun getById(id: Long): Observable<Item> {
		return lru[id]?.let { Observable.just(it) } ?: Observable.empty()
	}

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun save(item: Item): Observable<Boolean> {
		lru.put(item.id, item)
		return Observable.just(true)
	}

	// Private functions

	private fun getChildrenObservable(item: Item, level: Int): Observable<Item> {
		return Observable.from(item.kids.orEmpty())
				.concatMapEager { getById(it) }
				.map { it.copy(level = level) }
				.concatMapEager { getChildrenObservable(it, level + 1) }
	}
}
