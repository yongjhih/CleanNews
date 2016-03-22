package clean.news.ui.main

import clean.news.navigation.NavigationFactory.MainKey
import flow.ClassKey
import flow.TreeKey

class MainScreen : ClassKey(), TreeKey, MainKey {
	override fun getParentKey() = null
}
