package clean.news.app.repository.item

import clean.news.app.repository.Repository
import clean.news.core.entity.Item
import rx.Observable

interface ItemRepository : Repository<Item, Long> {
	fun getTopStories(): Observable<List<Item>>

	fun getNewStories(): Observable<List<Item>>

	fun getAskStories(): Observable<List<Item>>

	fun getShowStories(): Observable<List<Item>>

	fun getJobStories(): Observable<List<Item>>

	fun getChildren(item: Item): Observable<List<Item>>
}
