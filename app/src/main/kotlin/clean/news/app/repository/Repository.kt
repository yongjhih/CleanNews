package clean.news.app.repository

import rx.Observable

interface Repository<T, ID> {
	fun getAll(): Observable<List<T>>

	fun getById(id: ID): Observable<T>

	fun save(t: T): Observable<Boolean>
}
