package clean.news.sqlite

import clean.news.entity.User
import clean.news.repository.user.UserDiskRepository
import rx.Observable

class UserSqliteRepository : UserDiskRepository {
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
