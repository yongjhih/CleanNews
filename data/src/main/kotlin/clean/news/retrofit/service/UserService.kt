package clean.news.retrofit.service

import clean.news.entity.User
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface UserService {
	@GET("/user/{user_id}")
	fun getById(@Path("user_id") id: String): Observable<User>
}
