package clean.news.data.sqlite

import clean.news.app.data.item.ItemDiskDataSource
import clean.news.core.entity.Item
import clean.news.core.entity.Item.ListType
import io.reactivex.Observable

class ItemSqliteDataSource : ItemDiskDataSource {

	override fun getItems(listType: ListType): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun getChildren(item: Item): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun getAll(): Observable<List<Item>> {
		return Observable.empty()
	}

	override fun get(key: Long): Observable<Item> {
		return Observable.empty()
	}

	override fun put(key: Long, value: Item): Observable<Item> {
		return Observable.empty()
	}

	override fun remove(key: Long): Observable<Item> {
		return Observable.empty()
	}

}
