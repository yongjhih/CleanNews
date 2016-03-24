package clean.news.ui.item.detail

import clean.news.R
import clean.news.flow.WithComponent
import clean.news.flow.WithLayout
import clean.news.presentation.navigation.NavigationFactory.ItemDetailKey
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
class ItemDetailScreen(val id: Long) : ClassKey(),
		TreeKey,
		ItemDetailKey,
		WithLayout,
		WithComponent<MainComponent>,
		PaperParcelable {

	override fun getParentKey() = MainScreen()

	override fun getLayoutResId() = R.layout.item_detail_view

	override fun createComponent(parent: MainComponent) = parent.plus(ItemDetailModule(id))

	@Subcomponent(modules = arrayOf(ItemDetailModule::class))
	interface ItemDetailComponent {
		fun inject(view: ItemDetailView)
	}

	@Module
	class ItemDetailModule(val id: Long) {
		@Provides
		fun itemId(): Long = id
	}
}
