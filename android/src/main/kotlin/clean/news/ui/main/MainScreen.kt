package clean.news.ui.main

import clean.news.flow.WithComponent
import clean.news.inject.component.MainActivityComponent
import clean.news.navigation.NavigationFactory.MainKey
import flow.ClassKey
import flow.TreeKey

@WithComponent(MainActivityComponent::class)
class MainScreen : ClassKey(), TreeKey, MainKey {
	override fun getParentKey() = null
}
