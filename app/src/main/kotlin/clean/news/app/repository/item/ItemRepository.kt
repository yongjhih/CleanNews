package clean.news.app.repository.item

import clean.news.app.repository.Repository
import clean.news.core.entity.Item
import rx.Observable

interface ItemRepository : Repository<Item, Long> {
	fun getItems(listType: Item.ListType): Observable<List<Item>>

	fun getChildren(item: Item): Observable<List<Item>>
}
