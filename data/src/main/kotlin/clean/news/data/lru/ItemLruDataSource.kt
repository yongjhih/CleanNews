package clean.news.data.lru

import android.util.LruCache
import clean.news.app.data.item.ItemMemoryDataSource
import clean.news.core.entity.Item
import clean.news.core.entity.Item.ListType
import clean.news.core.entity.Item.Type
import io.reactivex.Observable

class ItemLruDataSource : ItemMemoryDataSource {

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
		return Observable.empty()
	}

	override fun get(key: Long): Observable<Item> {
		return lru[key]?.let { Observable.just(it) } ?: Observable.empty()
	}

	override fun put(key: Long, value: Item): Observable<Item> {
		return Observable.empty()
	}

	override fun remove(key: Long): Observable<Item> {
		return Observable.empty()
	}

	// Private functions

	private fun getChildrenObservable(item: Item, level: Int): Observable<Item> {
		return Observable.fromIterable(item.kids.orEmpty())
				.concatMapEager { get(it) }
				.map { it.copy(level = level) }
				.concatMapEager { getChildrenObservable(it, level + 1) }
	}

}
