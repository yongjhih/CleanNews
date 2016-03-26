package clean.news.navigation

import clean.news.core.entity.Item
import clean.news.presentation.navigation.NavigationFactory
import clean.news.ui.item.detail.ItemDetailScreen
import clean.news.ui.item.url.ItemUrlScreen
import clean.news.ui.main.MainScreen

class FlowNavigationFactory : NavigationFactory {
	override fun main() = MainScreen()

	override fun itemDetail(item: Item) = ItemDetailScreen(item)

	override fun url(url: String) = ItemUrlScreen(url)
}
