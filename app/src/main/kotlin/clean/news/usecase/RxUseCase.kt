package clean.news.usecase

import rx.Observable

interface RxUseCase<R> {
	fun execute(): Observable<R>
}
