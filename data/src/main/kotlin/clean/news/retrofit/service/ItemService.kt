package clean.news.retrofit.service

import clean.news.entity.Item
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface ItemService {
	@GET("topstories")
	fun getTopStories(): Observable<List<Long>>

	@GET("newstories")
	fun getNewStories(): Observable<List<Long>>

	@GET("askstories")
	fun getAskStories(): Observable<List<Long>>

	@GET("showstories")
	fun getShowStories(): Observable<List<Long>>

	@GET("jobstories")
	fun getJobStories(): Observable<List<Long>>

	@GET("item/{item_id}")
	fun getById(@Path("item_id") id: Long): Observable<Item>
}
