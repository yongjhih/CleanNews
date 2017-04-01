package clean.news.app.usecase

import clean.news.app.usecase.UseCase.Request
import clean.news.app.usecase.UseCase.Response
import io.reactivex.Observable

interface UseCase<in I : Request, O : Response> {

	fun execute(request: I): Observable<O>

	interface Request

	interface Response

}
