package clean.news.data.sqlite

import clean.news.app.repository.user.UserDiskRepository
import clean.news.core.entity.User
import rx.Observable

class UserSqliteRepository : UserDiskRepository {
	override fun getAll(): Observable<List<User>> {
		throw UnsupportedOperationException()
	}

	override fun getById(id: String): Observable<User> {
		throw UnsupportedOperationException()
	}

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun save(user: User): Observable<Boolean> {
		throw UnsupportedOperationException()
	}
}
