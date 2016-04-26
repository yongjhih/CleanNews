package clean.news.app.usecase.item

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.app.repository.item.ItemMemoryRepository
import clean.news.app.repository.item.ItemNetworkRepository
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.item.GetComments.Request
import clean.news.app.usecase.item.GetComments.Response
import clean.news.core.entity.Item
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject

class GetComments @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository,
		private val saveItem: SaveItem) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		return Strategy(request.flags)
				.execute(
						disk.getComments(request.item),
						memory.getComments(request.item),
						network.getComments(request.item),
						save
				)
				.map { Response(it) }
				.subscribeOn(Schedulers.io())
	}

	val save = { items: List<Item> ->
		items.forEach {
			saveItem.execute(SaveItem.Request(it)).subscribe()
		}
	}

	class Request(val item: Item, flags: Int = Strategy.WARM) : Strategy.Request(flags)

	class Response(val items: List<Item>) : UseCase.Response
}
