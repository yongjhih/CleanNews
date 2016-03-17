package clean.news.retrofit

import clean.news.entity.User
import clean.news.repository.user.UserNetworkRepository
import rx.Observable

class UserRetrofitRepository : UserNetworkRepository {
	override fun getAll(): Observable<List<User>> {
		throw UnsupportedOperationException()
	}

	override fun getById(id: String): Observable<User> {
		throw UnsupportedOperationException()
	}

	override fun save(t: User): Observable<Boolean> {
		throw UnsupportedOperationException()
	}
}
