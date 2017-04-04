package clean.news.data.retrofit.service

import clean.news.core.entity.User
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
	@GET("/user/{user_id}.json")
	fun getById(@Path("user_id") id: String): Maybe<User>
}
