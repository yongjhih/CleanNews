package clean.news.ui.item.list

import clean.news.R
import clean.news.flow.WithComponent
import clean.news.flow.WithLayout
import clean.news.ui.main.MainScreen.MainComponent
import dagger.Module
import dagger.Subcomponent
import flow.ClassKey

class ItemListScreen(val type: String) : ClassKey(), WithLayout, WithComponent<MainComponent> {
	override fun getLayoutResId() = R.layout.item_list_view

	override fun createComponent(parent: MainComponent) = parent.plus(ItemListModule())

	@Subcomponent(modules = arrayOf(ItemListModule::class))
	interface ItemListComponent {
		fun inject(itemListView: ItemListView)
	}

	@Module
	class ItemListModule
}
