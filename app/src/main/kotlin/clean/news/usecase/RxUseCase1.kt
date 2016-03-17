package clean.news.usecase

import rx.Observable

interface RxUseCase1<A, R> {
	fun execute(a: A, flags: Int = Strategy.WARM): Observable<R>
}
