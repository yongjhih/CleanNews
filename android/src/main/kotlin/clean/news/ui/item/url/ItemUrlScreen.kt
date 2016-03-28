package clean.news.ui.item.url

import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.WithComponent
import clean.news.flow.WithLayout
import clean.news.presentation.navigation.NavigationFactory.ItemUrlKey
import clean.news.ui.main.MainScreen
import clean.news.ui.main.MainScreen.MainComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import flow.TreeKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class ItemUrlScreen(val item: Item) : ClassKey(),
		TreeKey,
		ItemUrlKey,
		WithLayout,
		WithComponent<MainComponent>,
		PaperParcelable {

	override fun getParentKey() = MainScreen()

	override fun getLayoutResId() = R.layout.item_url_view

	override fun createComponent(parent: MainComponent) = parent.plus(ItemUrlModule(item))

	@Subcomponent(modules = arrayOf(ItemUrlModule::class))
	interface ItemUrlComponent {
		fun inject(view: ItemUrlView)
	}

	@Module
	class ItemUrlModule(private val item: Item) {
		@Provides
		fun item(): Item = item
	}
}
