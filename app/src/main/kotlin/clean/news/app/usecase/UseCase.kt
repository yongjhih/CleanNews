package clean.news.app.usecase

import rx.Observable

interface UseCase<I : UseCase.Request, O : UseCase.Response> {

	fun execute(request: I): Observable<O>

	interface Request

	interface Response

}
