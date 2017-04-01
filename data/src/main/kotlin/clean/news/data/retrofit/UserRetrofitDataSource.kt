package clean.news.data.retrofit

import clean.news.app.data.user.UserNetworkDataSource
import clean.news.core.entity.User
import clean.news.data.retrofit.service.UserService
import io.reactivex.Observable
import javax.inject.Inject

class UserRetrofitDataSource @Inject constructor(
		private val userService: UserService) : UserNetworkDataSource {

	override fun getAll(): Observable<List<User>> {
		throw UnsupportedOperationException("Cannot get all users from the network.")
	}

	override fun get(key: String): Observable<User> {
		return userService.getById(key)
	}

	override fun put(key: String, value: User): Observable<User> {
		throw UnsupportedOperationException("Cannot put a user to the network.")
	}

	override fun remove(key: String): Observable<User> {
		throw UnsupportedOperationException("Cannot remove a user from the network.")
	}

}
