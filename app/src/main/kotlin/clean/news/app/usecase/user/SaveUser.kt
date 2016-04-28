package clean.news.app.usecase.user

import clean.news.app.data.user.UserDiskDataSource
import clean.news.app.data.user.UserMemoryDataSource
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.user.SaveUser.Request
import clean.news.app.usecase.user.SaveUser.Response
import clean.news.core.entity.User
import rx.Observable

class SaveUser(
		private val disk: UserDiskDataSource,
		private val memory: UserMemoryDataSource) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		val strategy = Strategy(request.flags)
		val observable = Observable.empty<Boolean>()

		if (strategy.useDisk) observable.mergeWith(disk.save(request.user))
		if (strategy.useMemory) observable.mergeWith(memory.save(request.user))

		return observable.map { Response(it) }
	}

	class Request(val user: User, flags: Int = Strategy.WARM) : Strategy.Request(flags)

	class Response(val success: Boolean) : UseCase.Response
}
