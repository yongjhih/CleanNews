package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.usecase.RxUseCase1
import clean.news.usecase.Strategy
import rx.Observable

class SaveItem(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository) : RxUseCase1<Item, Boolean> {

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun execute(item: Item, flags: Int): Observable<Boolean> {
		val strategy = Strategy(flags)
		val observable = Observable.empty<Boolean>()

		if (strategy.useDisk) observable.mergeWith(disk.save(item))
		if (strategy.useMemory) observable.mergeWith(memory.save(item))

		return observable
	}
}
