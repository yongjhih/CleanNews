package clean.news.app.usecase

import io.reactivex.Maybe
import io.reactivex.Observable

class Strategy(private val flag: Int) {
	val useDisk: Boolean by lazy { flag and Strategy.Companion.DISK != 0 }
	val useMemory: Boolean by lazy { flag and Strategy.Companion.MEMORY != 0 }
	val useNetwork: Boolean by lazy { flag and Strategy.Companion.NETWORK != 0 }

	fun <T> execute(
			diskObservable: Observable<T>, // To Maybe?
			memoryObservable: Observable<T>, // To Maybe?
			networkObservable: Observable<T>, // To Maybe?
			save: (T) -> Any?): Observable<T> { // To Maybe?

		val observables = mutableListOf<Observable<T>>()
		val network = networkObservable.doOnNext { save(it) }
		val memory = memoryObservable.singleElement().onErrorResumeNext(Maybe.never()).takeUntil(network.singleElement())
		val disk = diskObservable.singleElement().onErrorResumeNext(Maybe.never()).takeUntil(memory)

		if (useMemory) observables.add(memory.toObservable()) // To Maybe?
		if (useDisk) observables.add(disk.toObservable()) // To Maybe?
		if (useNetwork) observables.add(network) // To Maybe?

		return Observable.merge(observables)
	}

	abstract class Request(val flags: Int) : UseCase.Request

	companion object {
		const val DISK = 1
		const val MEMORY = 2
		const val NETWORK = 4

		const val WARM = Strategy.Companion.DISK or Strategy.Companion.MEMORY or Strategy.Companion.NETWORK
	}
}
