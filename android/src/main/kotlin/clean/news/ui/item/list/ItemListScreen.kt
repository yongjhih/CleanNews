package clean.news.ui.item.list

import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.WithComponent
import clean.news.flow.WithLayout
import clean.news.ui.main.MainScreen.MainComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey

class ItemListScreen(private val listType: Item.ListType) : ClassKey(), WithLayout, WithComponent<MainComponent> {
	fun getTitle(): String {
		return listType.name
	}

	override fun getLayoutResId() = R.layout.item_list_view

	override fun createComponent(parent: MainComponent) = parent.plus(ItemListModule(listType))

	@Subcomponent(modules = arrayOf(ItemListModule::class))
	interface ItemListComponent {
		fun inject(itemListView: ItemListView)
	}

	@Module
	class ItemListModule(private val listType: Item.ListType) {
		@Provides
		fun listType(): Item.ListType {
			return listType
		}
	}
}
