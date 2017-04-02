package clean.news.app.usecase.item

import clean.news.app.data.item.ItemDiskDataSource
import clean.news.app.data.item.ItemMemoryDataSource
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.item.SaveItem.Request
import clean.news.app.usecase.item.SaveItem.Response
import clean.news.core.entity.Item
import io.reactivex.Observable
import javax.inject.Inject

class SaveItem @Inject constructor(
		private val disk: ItemDiskDataSource,
		private val memory: ItemMemoryDataSource) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		val strategy = Strategy(request.flags)
		val observable = Observable.empty<Item>()

		if (strategy.useDisk) observable.mergeWith(disk.put(request.item.id, request.item))
		if (strategy.useMemory) observable.mergeWith(memory.put(request.item.id, request.item))

		return observable.map { Response() }
	}

	class Request(val item: Item, flags: Int = Strategy.WARM) : Strategy.Request(flags)

	class Response() : UseCase.Response
}
