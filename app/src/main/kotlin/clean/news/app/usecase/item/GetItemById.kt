package clean.news.app.usecase.item

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.app.repository.item.ItemMemoryRepository
import clean.news.app.repository.item.ItemNetworkRepository
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.item.GetItemById.Request
import clean.news.app.usecase.item.GetItemById.Response
import clean.news.core.entity.Item
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject

class GetItemById @Inject constructor(
		private val disk: ItemDiskRepository,
		private val memory: ItemMemoryRepository,
		private val network: ItemNetworkRepository,
		private val saveItem: SaveItem) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		return Strategy(request.flags)
				.execute(
						disk.getById(request.id),
						memory.getById(request.id),
						network.getById(request.id),
						save
				)
				.map { Response(it) }
				.subscribeOn(Schedulers.io())
	}

	private val save = { item: Item ->
		saveItem.execute(SaveItem.Request(item)).subscribe()
	}

	class Request(val id: Long, val flags: Int = Strategy.WARM) : UseCase.Request

	class Response(val item: Item) : UseCase.Response
}
