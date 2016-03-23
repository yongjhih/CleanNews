package clean.news.ui.item.detail

import clean.news.flow.WithComponent
import clean.news.presentation.navigation.NavigationFactory.ItemDetailKey
import clean.news.ui.main.MainScreen
import clean.news.ui.main.MainScreen.MainComponent
import dagger.Module
import dagger.Subcomponent
import flow.ClassKey
import flow.TreeKey

class ItemDetailScreen(private val id: Long) : ClassKey(), TreeKey, ItemDetailKey, WithComponent<MainComponent> {
	override fun getParentKey() = MainScreen()

	override fun createComponent(parent: MainComponent) = parent.plus(ItemDetailModule(id))

	@Subcomponent(modules = arrayOf(ItemDetailModule::class))
	interface ItemDetailComponent

	@Module
	class ItemDetailModule(val id: Long)
}
