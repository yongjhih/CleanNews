package clean.news.data.retrofit

import clean.news.app.repository.user.UserNetworkRepository
import clean.news.core.entity.User
import clean.news.data.retrofit.service.UserService
import rx.Observable
import javax.inject.Inject

class UserRetrofitRepository @Inject constructor(
		private val userService: UserService) : UserNetworkRepository {

	override fun getAll(): Observable<List<User>> {
		throw UnsupportedOperationException("Cannot get all users from network API.")
	}

	override fun getById(id: String): Observable<User> {
		return userService.getById(id)
	}

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun save(user: User): Observable<Boolean> {
		throw UnsupportedOperationException("Cannot save a user to the network API.")
	}
}
