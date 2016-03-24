package clean.news.ui.item.url

import clean.news.R
import clean.news.flow.WithComponent
import clean.news.flow.WithLayout
import clean.news.presentation.navigation.NavigationFactory.UrlKey
import clean.news.ui.main.MainScreen
import clean.news.ui.main.MainScreen.MainComponent
import dagger.Module
import dagger.Subcomponent
import flow.ClassKey
import flow.TreeKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class ItemUrlScreen(val url: String) : ClassKey(),
		TreeKey,
		UrlKey,
		WithLayout,
		WithComponent<MainComponent>,
		PaperParcelable {

	override fun getLayoutResId() = R.layout.item_url_view

	override fun createComponent(parent: MainComponent) = parent.plus(ItemUrlModule(url))

	override fun getParentKey(): Any = MainScreen()

	@Subcomponent(modules = arrayOf(ItemUrlModule::class))
	interface ItemUrlComponent

	@Module
	class ItemUrlModule(val url: String)
}
