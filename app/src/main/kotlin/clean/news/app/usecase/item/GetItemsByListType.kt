package clean.news.app.usecase.item

import clean.news.app.data.item.ItemDiskDataSource
import clean.news.app.data.item.ItemMemoryDataSource
import clean.news.app.data.item.ItemNetworkDataSource
import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.item.GetItemsByListType.Request
import clean.news.app.usecase.item.GetItemsByListType.Response
import clean.news.core.entity.Item
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class GetItemsByListType @Inject constructor(
		private val disk: ItemDiskDataSource,
		private val memory: ItemMemoryDataSource,
		private val network: ItemNetworkDataSource,
		private val saveItem: SaveItem) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		return Strategy(request.flags)
				.execute(
						disk.getItems(request.listType),
						memory.getItems(request.listType),
						network.getItems(request.listType),
						save
				)
				.subscribeOn(Schedulers.io())
				.map { Response(it) }
	}

	val save = { items: List<Item> ->
		items.forEach {
			saveItem.execute(SaveItem.Request(it)).subscribe()
		}
	}

	class Request(val listType: Item.ListType, flags: Int = Strategy.WARM) : Strategy.Request(flags)

	class Response(val items: List<Item>) : UseCase.Response
}
