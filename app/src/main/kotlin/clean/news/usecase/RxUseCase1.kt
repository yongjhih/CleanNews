package clean.news.usecase

import rx.Observable

interface RxUseCase1<A, R> {
	fun execute(a: A): Observable<R>
}
