package clean.news.data.retrofit.service

import clean.news.core.entity.Item
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemService {
	@GET("topstories.json")
	fun getTopStories(): Maybe<List<Long>>

	@GET("newstories.json")
	fun getNewStories(): Maybe<List<Long>>

	@GET("askstories.json")
	fun getAskStories(): Maybe<List<Long>>

	@GET("showstories.json")
	fun getShowStories(): Maybe<List<Long>>

	@GET("jobstories.json")
	fun getJobStories(): Maybe<List<Long>>

	@GET("item/{item_id}.json")
	fun getById(@Path("item_id") id: Long): Maybe<Item>
}
