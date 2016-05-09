package clean.news.navigation

import clean.news.core.entity.Item
import clean.news.presentation.navigation.NavigationFactory
import clean.news.ui.item.detail.ItemDetailKey
import clean.news.ui.item.detail.ItemDetailShareKey
import clean.news.ui.item.url.ItemUrlKey
import clean.news.ui.item.url.ItemUrlShareKey
import clean.news.ui.main.MainKey

class FlowNavigationFactory : NavigationFactory {
	override fun main() = MainKey()

	override fun itemDetail(item: Item) = ItemDetailKey(item)

	override fun url(item: Item) = ItemUrlKey(item)

	override fun shareDetail(item: Item) = ItemDetailShareKey(item)

	override fun shareUrl(item: Item) = ItemUrlShareKey(item)
}
