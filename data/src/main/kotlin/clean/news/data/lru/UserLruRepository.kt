package clean.news.data.lru

import android.util.LruCache
import clean.news.entity.User
import clean.news.repository.user.UserMemoryRepository
import rx.Observable

class UserLruRepository : UserMemoryRepository {
	val lru = LruCache<String, User>(512)

	override fun getAll(): Observable<List<User>> {
		return Observable.just(lru.snapshot().values.toList())
	}

	override fun getById(id: String): Observable<User> {
		return Observable.just(lru[id])
	}

	override fun save(user: User): Observable<Boolean> {
		lru.put(user.id, user)
		return Observable.just(true)
	}
}
