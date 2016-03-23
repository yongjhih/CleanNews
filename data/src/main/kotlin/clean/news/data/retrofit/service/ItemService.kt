package clean.news.data.retrofit.service

import clean.news.core.entity.Item
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface ItemService {
	@GET("topstories.json")
	fun getTopStories(): Observable<List<Long>>

	@GET("newstories.json")
	fun getNewStories(): Observable<List<Long>>

	@GET("askstories.json")
	fun getAskStories(): Observable<List<Long>>

	@GET("showstories.json")
	fun getShowStories(): Observable<List<Long>>

	@GET("jobstories.json")
	fun getJobStories(): Observable<List<Long>>

	@GET("item/{item_id}.json")
	fun getById(@Path("item_id") id: Long): Observable<Item>
}
