package clean.news.ui.item.list

import clean.news.core.entity.Item
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.ui.main.MainKey.MainComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
class ItemListKey(val listType: Item.ListType) : ClassKey(),
		WithComponent,
		PaperParcelable {

	override fun createComponent(parent: Any): Any {
		if (parent !is MainComponent) {
			throw IllegalArgumentException()
		}
		return parent.plus(ItemListModule(listType))
	}

	@Subcomponent(modules = arrayOf(ItemListModule::class))
	interface ItemListComponent {
		fun inject(itemListView: ItemListView)
	}

	@Module
	class ItemListModule(private val listType: Item.ListType) {
		@Provides
		fun listType(): Item.ListType = listType
	}

}
