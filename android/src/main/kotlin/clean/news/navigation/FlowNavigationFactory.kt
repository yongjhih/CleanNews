package clean.news.navigation

import clean.news.core.entity.Item
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationFactory.ItemShareUrlKey
import clean.news.ui.item.detail.ItemDetailScreen
import clean.news.ui.item.detail.ItemDetailShareScreen
import clean.news.ui.item.url.ItemUrlScreen
import clean.news.ui.item.url.ItemUrlShareScreen
import clean.news.ui.main.MainScreen

class FlowNavigationFactory : NavigationFactory {
	override fun main() = MainScreen()

	override fun itemDetail(item: Item) = ItemDetailScreen(item)

	override fun url(item: Item) = ItemUrlScreen(item)

	override fun shareDetail(item: Item) = ItemDetailShareScreen(item)

	override fun shareUrl(item: Item): ItemShareUrlKey = ItemUrlShareScreen(item)
}
