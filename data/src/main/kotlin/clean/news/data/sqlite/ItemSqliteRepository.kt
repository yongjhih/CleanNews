package clean.news.data.sqlite

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.core.entity.Item
import rx.Observable

class ItemSqliteRepository : ItemDiskRepository {
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
		return Observable.empty()
	}

	override fun getChildren(item: Item): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun getAll(): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun getById(id: Long): Observable<Item> {
		return Observable.empty()
	}

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun save(item: Item): Observable<Boolean> {
		return Observable.empty()
	}
}
