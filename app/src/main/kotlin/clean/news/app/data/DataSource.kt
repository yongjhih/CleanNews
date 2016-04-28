package clean.news.app.data

import rx.Observable

interface DataSource<T, ID> {
	fun getAll(): Observable<List<T>>

	fun getById(id: ID): Observable<T>

	fun save(t: T): Observable<Boolean>
}
