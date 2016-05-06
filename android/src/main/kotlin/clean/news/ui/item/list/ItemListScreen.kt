package clean.news.ui.item.list

import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.keychanger.SceneKeyChanger.WithLayout
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.ui.main.MainKey.MainComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class ItemListScreen(val listType: Item.ListType) : ClassKey(),
		WithLayout,
		WithComponent,
		PaperParcelable {

	fun getTitle(): String {
		return listType.name
	}

	override fun getLayoutResId() = R.layout.item_list_view

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
