package clean.news.app.usecase.item

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.app.repository.item.ItemMemoryRepository
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.item.SaveItem.Request
import clean.news.app.usecase.item.SaveItem.Response
import clean.news.core.entity.Item
import rx.Observable
import javax.inject.Inject

class SaveItem @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		val strategy = Strategy(request.flags)
		val observable = Observable.empty<Boolean>()

		if (strategy.useDisk) observable.mergeWith(disk.save(request.item))
		if (strategy.useMemory) observable.mergeWith(memory.save(request.item))

		return observable.map { Response(it) }
	}

	class Request(val item: Item, flags: Int = Strategy.WARM) : Strategy.Request(flags)

	class Response(val success: Boolean) : UseCase.Response
}
