package clean.news.usecase

import rx.Observable

interface RxUseCase<R> {
	fun execute(flags: Int = Strategy.WARM): Observable<R>
}
