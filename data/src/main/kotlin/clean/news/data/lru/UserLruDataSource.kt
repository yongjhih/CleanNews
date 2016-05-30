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

	override fun get(key: String): Observable<User> {
		return Observable.just(lru[key])
	}

	override fun put(key: String, value: User): Observable<User> {
		return Observable.just(lru.put(key, value))
	}

	override fun remove(key: String): Observable<User> {
		return Observable.just(lru.remove(key))
	}

}
