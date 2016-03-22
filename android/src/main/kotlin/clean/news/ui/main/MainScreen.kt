package clean.news.ui.main

import clean.news.R
import clean.news.flow.HasLayout
import clean.news.flow.WithComponent
import clean.news.inject.component.MainActivityComponent
import clean.news.presentation.navigation.NavigationFactory.MainKey
import flow.ClassKey
import flow.TreeKey

@WithComponent(MainActivityComponent::class)
class MainScreen : ClassKey(), TreeKey, MainKey, HasLayout {
	override fun getParentKey() = null

	override fun getLayoutResId() = R.layout.main_screen
}
