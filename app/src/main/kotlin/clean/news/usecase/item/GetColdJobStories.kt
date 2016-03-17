package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.gateway.ItemGateway
import clean.news.usecase.RxUseCase
import rx.Observable
import javax.inject.Inject

class GetColdJobStories @Inject constructor(
		private val itemGateway: ItemGateway) : RxUseCase<List<Item>> {

	override fun execute(): Observable<List<Item>> {
		return itemGateway.getJobStories()
	}
}
