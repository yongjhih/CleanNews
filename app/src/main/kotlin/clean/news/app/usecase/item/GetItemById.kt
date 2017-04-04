package clean.news.app.usecase.item

import clean.news.app.data.item.ItemDiskDataSource
import clean.news.app.data.item.ItemMemoryDataSource
import clean.news.app.data.item.ItemNetworkDataSource
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.item.GetItemById.Request
import clean.news.app.usecase.item.GetItemById.Response
import clean.news.core.entity.Item
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetItemById @Inject constructor(
		private val disk: ItemDiskDataSource,
		private val memory: ItemMemoryDataSource,
		private val network: ItemNetworkDataSource,
		private val saveItem: SaveItem) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		return Strategy(request.flags)
				.execute(
						disk.get(request.id),
						memory.get(request.id),
						network.get(request.id),
						save
				)
				.map { Response(it) }
				.subscribeOn(Schedulers.io())
	}

	private val save = { item: Item ->
		saveItem.execute(SaveItem.Request(item)).subscribe({}, { e -> e.printStackTrace() })
	}

	class Request(val id: Long, flags: Int = Strategy.WARM) : Strategy.Request(flags)

	class Response(val item: Item) : UseCase.Response
}
