package clean.news.ui.item.detail

import clean.news.navigation.NavigationFactory.ItemDetailKey
import clean.news.ui.main.MainScreen
import flow.ClassKey
import flow.TreeKey

class ItemDetailScreen(val id: Long) : ClassKey(), TreeKey, ItemDetailKey {
	override fun getParentKey() = MainScreen()
}
