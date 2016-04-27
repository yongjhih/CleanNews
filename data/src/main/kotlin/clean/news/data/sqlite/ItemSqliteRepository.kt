package clean.news.data.sqlite

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.core.entity.Item
import clean.news.core.entity.Item.ListType
import rx.Observable

class ItemSqliteRepository : ItemDiskRepository {
	override fun getItems(listType: ListType): Observable<List<Item>> {
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
