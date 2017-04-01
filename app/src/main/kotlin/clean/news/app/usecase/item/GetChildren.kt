package clean.news.app.usecase.item

import clean.news.app.data.item.ItemDiskDataSource
import clean.news.app.data.item.ItemMemoryDataSource
import clean.news.app.data.item.ItemNetworkDataSource
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.item.GetChildren.Request
import clean.news.app.usecase.item.GetChildren.Response
import clean.news.core.entity.Item
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetChildren @Inject constructor(
		private val disk: ItemDiskDataSource,
		private val memory: ItemMemoryDataSource,
		private val network: ItemNetworkDataSource,
		private val saveItem: SaveItem) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		return Strategy(request.flags)
				.execute(
						disk.getChildren(request.item),
						memory.getChildren(request.item),
						network.getChildren(request.item),
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
