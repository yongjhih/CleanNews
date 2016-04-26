package clean.news.app.usecase.user

import clean.news.app.repository.user.UserDiskRepository
import clean.news.app.repository.user.UserMemoryRepository
import clean.news.app.repository.user.UserNetworkRepository
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.user.GetUserById.Request
import clean.news.app.usecase.user.GetUserById.Response
import clean.news.core.entity.User
import rx.Observable
import javax.inject.Inject

class GetUserById @Inject constructor(
		private val disk: UserDiskRepository,
		private val memory: UserMemoryRepository,
		private val network: UserNetworkRepository,
		private val saveUser: SaveUser) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		return Strategy(request.flags)
				.execute(
						disk.getById(request.id),
						memory.getById(request.id),
						network.getById(request.id),
						save
				)
				.map { Response(it) }
	}

	private val save = { user: User ->
		saveUser.execute(SaveUser.Request(user)).subscribe()
	}

	class Request(val id: String, val flags: Int = Strategy.WARM) : UseCase.Request

	class Response(val user: User) : UseCase.Response
}
