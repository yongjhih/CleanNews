package clean.news.navigation

import clean.news.ui.item.detail.ItemDetailScreen
import clean.news.ui.item.list.ItemListScreen
import clean.news.ui.item.url.UrlScreen
import clean.news.ui.main.MainScreen

class FlowNavigationFactory : NavigationFactory {
	override fun mainKey() = MainScreen()

	override fun itemList() = ItemListScreen()

	override fun itemDetail(id: Long) = ItemDetailScreen(id)

	override fun url(url: String) = UrlScreen(url)
}
