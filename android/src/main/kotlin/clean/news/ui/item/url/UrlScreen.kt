package clean.news.ui.item.url

import clean.news.presentation.navigation.NavigationFactory.UrlKey
import clean.news.ui.main.MainScreen
import flow.ClassKey
import flow.TreeKey

class UrlScreen(val url: String) : ClassKey(), TreeKey, UrlKey {
	override fun getParentKey(): Any = MainScreen()
}
