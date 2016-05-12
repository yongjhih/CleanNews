package clean.news.app.usecase.user

import clean.news.app.data.user.UserDiskDataSource
import clean.news.app.data.user.UserMemoryDataSource
import clean.news.app.data.user.UserNetworkDataSource
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.user.GetUserById.Request
import clean.news.app.usecase.user.GetUserById.Response
import clean.news.core.entity.User
import rx.Observable
import javax.inject.Inject

class GetUserById @Inject constructor(
		private val disk: UserDiskDataSource,
		private val memory: UserMemoryDataSource,
		private val network: UserNetworkDataSource,
		private val saveUser: SaveUser) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		return Strategy(request.flags)
				.execute(
						disk.get(request.id),
						memory.get(request.id),
						network.get(request.id),
						save
				)
				.map { Response(it) }
	}

	private val save = { user: User ->
		saveUser.execute(SaveUser.Request(user)).subscribe()
	}

	class Request(val id: String, flags: Int = Strategy.WARM) : Strategy.Request(flags)

	class Response(val user: User) : UseCase.Response
}
