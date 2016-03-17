package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.repository.item.ItemNetworkRepository
import clean.news.usecase.RxUseCase1
import clean.news.usecase.Strategy
import rx.Observable
import rx.functions.Action1
import javax.inject.Inject

class GetItemById @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository,
		private val saveItem: SaveItem) : RxUseCase1<Long, Item> {

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun execute(id: Long, flags: Int): Observable<Item> {
		val strategy = Strategy(flags)
		val observables = Observable.empty<Item>()

		if (strategy.memory) observables.concatWith(memory.getById(id).onErrorResumeNext(Observable.empty()))
		if (strategy.disk) observables.concatWith(disk.getById(id).onErrorResumeNext(Observable.empty()))
		if (strategy.network) observables.concatWith(network.getById(id).doOnNext(save))

		return observables.let { if (strategy.first) it.first() else it }
	}

	private val save = Action1 { item: Item ->
		saveItem.execute(item).subscribe()
	}
}
