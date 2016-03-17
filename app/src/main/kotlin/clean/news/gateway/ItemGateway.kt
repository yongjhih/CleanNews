package clean.news.gateway

import clean.news.entity.Item
import rx.Observable

interface ItemGateway {
	fun getTopStories(): Observable<List<Item>>

	fun getNewStories(): Observable<List<Item>>

	fun getAskStories(): Observable<List<Item>>

	fun getShowStories(): Observable<List<Item>>

	fun getJobStories(): Observable<List<Item>>

	fun getItem(id: Long): Observable<Item>
}
