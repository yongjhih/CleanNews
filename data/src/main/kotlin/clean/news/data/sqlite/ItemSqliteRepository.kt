package clean.news.data.sqlite

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.core.entity.Item
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

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun save(item: Item): Observable<Boolean> {
		throw UnsupportedOperationException()
	}
}
