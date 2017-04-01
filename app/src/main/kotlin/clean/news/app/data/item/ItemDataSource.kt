package clean.news.app.data.item

import clean.news.app.data.DataSource
import clean.news.core.entity.Item
import clean.news.core.entity.Item.ListType
import io.reactivex.Observable

interface ItemDataSource : DataSource<Long, Item> {

	fun getItems(listType: ListType): Observable<List<Item>>

	fun getChildren(item: Item): Observable<List<Item>>

}
