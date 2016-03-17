package clean.news.sqlite

import clean.news.entity.Item
import clean.news.repository.item.ItemDiskRepository
import rx.Observable

class ItemSqliteRepository : ItemDiskRepository {
	override fun getTopStories(): Observable<List<Item>> {
		throw UnsupportedOperationException()
	}

	override fun getNewStories(): Observable<List<Item>> {
		throw UnsupportedOperationException()
	}

	override fun getAskStories(): Observable<List<Item>> {
		throw UnsupportedOperationException()
	}

	override fun getShowStories(): Observable<List<Item>> {
		throw UnsupportedOperationException()
	}

	override fun getJobStories(): Observable<List<Item>> {
		throw UnsupportedOperationException()
	}

	override fun getAll(): Observable<List<Item>> {
		throw UnsupportedOperationException()
	}

	override fun getById(id: Long): Observable<Item> {
		throw UnsupportedOperationException()
	}

	override fun save(t: Item): Observable<Boolean> {
		throw UnsupportedOperationException()
	}
}
