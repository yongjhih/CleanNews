package clean.news.repository.item

import clean.news.entity.Item
import clean.news.repository.Repository
import rx.Observable

interface ItemRepository : Repository<Item, Long> {
	fun getTopStories(): Observable<List<Item>>

	fun getNewStories(): Observable<List<Item>>

	fun getAskStories(): Observable<List<Item>>

	fun getShowStories(): Observable<List<Item>>

	fun getJobStories(): Observable<List<Item>>
}
