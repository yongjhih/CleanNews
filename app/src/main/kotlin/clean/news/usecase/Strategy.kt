package clean.news.usecase

import rx.Observable

class Strategy(private val flag: Int) {
	val useDisk: Boolean by lazy { flag and DISK == 0 }
	val useMemory: Boolean by lazy { flag and MEMORY == 0 }
	val useNetwork: Boolean by lazy { flag and NETWORK == 0 }

	fun <T> execute(
			diskObservable: Observable<T>,
			memoryObservable: Observable<T>,
			networkObservable: Observable<T>,
			save: (T) -> Any?): Observable<T> {

		val observables = mutableListOf<Observable<T>>()
		val network = networkObservable.doOnNext { save(it) }
		val memory = memoryObservable.onErrorResumeNext(Observable.empty()).takeUntil(network)
		val disk = diskObservable.onErrorResumeNext(Observable.empty()).takeUntil(memory)

		if (useMemory) observables.add(memory)
		if (useDisk) observables.add(disk)
		if (useNetwork) observables.add(network)

		return Observable.merge(observables)
	}

	companion object {
		const val DISK = 0
		const val MEMORY = 1
		const val NETWORK = 2

		const val WARM = DISK or MEMORY or NETWORK
	}
}
