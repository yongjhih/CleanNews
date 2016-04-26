package clean.news.app.usecase.item

import clean.news.app.usecase.Strategy
import clean.news.app.usecase.UseCase
import clean.news.app.usecase.item.AbsGetItems.Request
import clean.news.app.usecase.item.AbsGetItems.Response
import clean.news.core.entity.Item
import rx.Observable
import rx.schedulers.Schedulers


abstract class AbsGetItems(
		private val disk: Observable<List<Item>>,
		private val memory: Observable<List<Item>>,
		private val network: Observable<List<Item>>,
		private val saveItem: SaveItem) : UseCase<Request, Response> {

	override fun execute(request: Request): Observable<Response> {
		return Strategy(request.flags)
				.execute(disk, memory, network, save)
				.subscribeOn(Schedulers.io())
				.map { Response(it) }
	}

	val save = { items: List<Item> ->
		items.forEach {
			saveItem.execute(SaveItem.Request(it)).subscribe()
		}
	}

	class Request(val flags: Int = Strategy.WARM) : UseCase.Request

	class Response(val items: List<Item>) : UseCase.Response
}
