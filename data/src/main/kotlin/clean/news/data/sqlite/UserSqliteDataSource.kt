package clean.news.data.sqlite

import clean.news.app.data.user.UserDiskDataSource
import clean.news.core.entity.User
import rx.Observable

class UserSqliteDataSource : UserDiskDataSource {

	override fun getAll(): Observable<List<User>> {
		return Observable.empty()
	}

	override fun get(key: String): Observable<User> {
		return Observable.empty()
	}

	override fun put(key: String, value: User): Observable<User> {
		return Observable.empty()
	}

	override fun remove(key: String): Observable<User> {
		return Observable.empty()
	}

}
