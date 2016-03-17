package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.repository.item.ItemNetworkRepository
import clean.news.usecase.RxUseCase
import clean.news.usecase.Strategy
import rx.Observable
import javax.inject.Inject

class GetTopStories @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository,
		private val saveItem: SaveItem) : RxUseCase<List<Item>> {

	override fun execute(flags: Int): Observable<List<Item>> {
		val strategy = Strategy(flags)
		val observables = Observable.empty<List<Item>>()

		if (strategy.useMemory) observables.concatWith(memory.getTopStories().onErrorResumeNext(Observable.empty()))
		if (strategy.useDisk) observables.concatWith(disk.getTopStories().onErrorResumeNext(Observable.empty()))
		if (strategy.useNetwork) observables.concatWith(network.getTopStories().doOnNext(save))

		return observables
	}

	private val save = { items: List<Item> ->
		items.forEach {
			saveItem.execute(it).subscribe()
		}
	}
}
