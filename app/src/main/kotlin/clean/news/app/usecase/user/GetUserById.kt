package clean.news.app.usecase.user

import clean.news.app.repository.user.UserDiskRepository
import clean.news.app.repository.user.UserMemoryRepository
import clean.news.app.repository.user.UserNetworkRepository
import clean.news.app.usecase.RxUseCase1
import clean.news.app.usecase.Strategy
import clean.news.core.entity.User
import rx.Observable
import javax.inject.Inject

class GetUserById @Inject constructor(
		private val disk: UserDiskRepository,
		private val memory: UserMemoryRepository,
		private val network: UserNetworkRepository,
		private val saveUser: SaveUser) : RxUseCase1<String, User> {

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun execute(id: String, flags: Int): Observable<User> {
		val strategy = Strategy(flags)

		return strategy.execute(
				disk.getById(id),
				memory.getById(id),
				network.getById(id),
				save)
	}

	private val save = { user: User ->
		saveUser.execute(user).subscribe()
	}
}
