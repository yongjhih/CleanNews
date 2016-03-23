package clean.news.data.retrofit.service

import clean.news.core.entity.User
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface UserService {
	@GET("/user/{user_id}.json")
	fun getById(@Path("user_id") id: String): Observable<User>
}
