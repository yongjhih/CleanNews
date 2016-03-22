package clean.news.ui.item.list

import clean.news.presentation.navigation.NavigationFactory.ItemListKey
import clean.news.ui.main.MainScreen
import flow.ClassKey
import flow.TreeKey

class ItemListScreen : ClassKey(), TreeKey, ItemListKey {
	override fun getParentKey() = MainScreen()
}
