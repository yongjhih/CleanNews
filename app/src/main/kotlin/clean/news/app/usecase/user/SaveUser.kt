package clean.news.app.usecase.user

import clean.news.app.repository.user.UserDiskRepository
import clean.news.app.repository.user.UserMemoryRepository
import clean.news.app.usecase.RxUseCase1
import clean.news.app.usecase.Strategy
import clean.news.core.entity.User
import rx.Observable

class SaveUser(
		private val disk: UserDiskRepository,
		private val memory: UserMemoryRepository) : RxUseCase1<User, Boolean> {

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun execute(user: User, flags: Int): Observable<Boolean> {
		val strategy = Strategy(flags)
		val observable = Observable.empty<Boolean>()

		if (strategy.useDisk) observable.mergeWith(disk.save(user))
		if (strategy.useMemory) observable.mergeWith(memory.save(user))

		return observable
	}
}
