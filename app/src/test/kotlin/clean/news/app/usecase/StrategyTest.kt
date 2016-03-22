package clean.news.app.usecase

import clean.news.app.usecase.Strategy.Companion.DISK
import clean.news.app.usecase.Strategy.Companion.MEMORY
import clean.news.app.usecase.Strategy.Companion.NETWORK
import org.junit.Test
import rx.Observable
import rx.Observable.empty
import rx.Observable.just
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import java.util.concurrent.TimeUnit.SECONDS

class StrategyTest {
	@Test
	fun warmMemoryThenDiskThenNetwork() {
		val (disk, memory, network, scheduler) = createMockObservableSet(DISK or MEMORY or NETWORK)
		val strategy = Strategy(Strategy.WARM)
		val observable = strategy.execute(disk, memory, network) {}
		val subscriber = TestSubscriber.create<Int>()

		observable.subscribe(subscriber)

		scheduler.advanceTimeBy(networkDelay, SECONDS)
		subscriber.assertValues(memoryValue, networkValue)
	}

	@Test
	fun warmDiskThenNetwork() {
		val (disk, memory, network, scheduler) = createMockObservableSet(DISK or NETWORK)
		val strategy = Strategy(Strategy.WARM)
		val observable = strategy.execute(disk, memory, network) {}
		val subscriber = TestSubscriber.create<Int>()

		observable.subscribe(subscriber)

		scheduler.advanceTimeBy(networkDelay, SECONDS)
		subscriber.assertValues(diskValue, networkValue)
	}

	private fun createMockObservableSet(
			sourceFlags: Int,
			diskDelay: Long = StrategyTest.diskDelay,
			memoryDelay: Long = StrategyTest.memoryDelay,
			networkDelay: Long = StrategyTest.networkDelay): StrategyTest.MockSet<Int> {

		val strategy = Strategy(sourceFlags)
		val scheduler = TestScheduler()
		val disk = if (strategy.useDisk) just(diskValue).delay(diskDelay, SECONDS, scheduler) else empty()
		val memory = if (strategy.useMemory) just(memoryValue).delay(memoryDelay, SECONDS, scheduler) else empty()
		val network = if (strategy.useNetwork) just(networkValue).delay(networkDelay, SECONDS, scheduler) else empty()

		return StrategyTest.MockSet(disk, memory, network, scheduler)
	}

	private data class MockSet<T>(
			val diskObservable: Observable<T>,
			val memoryObservable: Observable<T>,
			val networkObservable: Observable<T>,
			val scheduler: TestScheduler)

	companion object {
		const val diskValue = 1
		const val memoryValue = 2
		const val networkValue = 3

		const val diskDelay: Long = 1
		const val memoryDelay: Long = 0
		const val networkDelay: Long = 2
	}
}
