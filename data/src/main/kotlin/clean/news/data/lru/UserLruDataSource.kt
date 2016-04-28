package clean.news.data.lru

import android.util.LruCache
import clean.news.app.data.user.UserMemoryDataSource
import clean.news.core.entity.User
import rx.Observable

class UserLruDataSource : UserMemoryDataSource {
	val lru = LruCache<String, User>(512)

	override fun getAll(): Observable<List<User>> {
		return Observable.just(lru.snapshot().values.toList())
	}

	override fun getById(id: String): Observable<User> {
		return Observable.just(lru[id])
	}

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun save(user: User): Observable<Boolean> {
		lru.put(user.id, user)
		return Observable.just(true)
	}
}
