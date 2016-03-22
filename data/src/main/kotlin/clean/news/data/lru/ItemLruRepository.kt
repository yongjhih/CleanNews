package clean.news.data.lru

import android.util.LruCache
import clean.news.entity.Item
import clean.news.entity.Item.Type
import clean.news.repository.item.ItemMemoryRepository
import rx.Observable

class ItemLruRepository : ItemMemoryRepository {
	val lru = LruCache<Long, Item>(512)

	override fun getTopStories(): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun getNewStories(): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun getAskStories(): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun getShowStories(): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun getJobStories(): Observable<List<Item>> {
		return Observable.just(lru.snapshot().values.filter { it.type == Type.JOB })
	}

	override fun getAll(): Observable<List<Item>> {
		return Observable.just(lru.snapshot().values.toList())
	}

	override fun getById(id: Long): Observable<Item> {
		return Observable.just(lru[id])
	}

	override fun save(item: Item): Observable<Boolean> {
		lru.put(item.id, item)
		return Observable.just(true)
	}
}
