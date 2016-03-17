package clean.news.usecase.item

import clean.news.entity.Item
import clean.news.gateway.ItemGateway
import clean.news.usecase.RxUseCase1
import rx.Observable
import javax.inject.Inject

class GetColdItem @Inject constructor(
		private val itemGateway: ItemGateway) : RxUseCase1<Long, Item> {

	@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
	override fun execute(id: Long): Observable<Item> {
		return itemGateway.getItem(id)
	}
}
