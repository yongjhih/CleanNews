package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.repository.item.ItemNetworkRepository
import clean.news.usecase.RxUseCase
import clean.news.usecase.Strategy
import rx.Observable
import javax.inject.Inject

class GetNewStories @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository,
		private val saveItem: SaveItem) : RxUseCase<List<Item>> {

	override fun execute(flags: Int): Observable<List<Item>> {
		val strategy = Strategy(flags)
		val observables = Observable.empty<List<Item>>()

		if (strategy.memory) observables.concatWith(memory.getNewStories().onErrorResumeNext(Observable.empty()))
		if (strategy.disk) observables.concatWith(disk.getNewStories().onErrorResumeNext(Observable.empty()))
		if (strategy.network) observables.concatWith(network.getNewStories().doOnNext(save))

		return observables.let { if (strategy.first) it.first() else it }
	}

	private val save = { items: List<Item> ->
		items.forEach {
			saveItem.execute(it).subscribe()
		}
	}
}
