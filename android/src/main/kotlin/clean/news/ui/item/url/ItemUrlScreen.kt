package clean.news.ui.item.url

import android.transition.ChangeBounds
import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.WithComponent
import clean.news.flow.WithLayout
import clean.news.flow.WithTransition
import clean.news.presentation.navigation.NavigationFactory.ItemUrlKey
import clean.news.ui.item.url.ItemUrlShareScreen.ItemUrlShareModule
import clean.news.ui.main.MainScreen
import clean.news.ui.main.MainScreen.MainComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import flow.Direction
import flow.TreeKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class ItemUrlScreen(val item: Item) : ClassKey(),
		TreeKey,
		ItemUrlKey,
		WithLayout,
		WithTransition,
		WithComponent<MainComponent>,
		PaperParcelable {

	override fun getParentKey() = MainScreen()

	override fun getLayoutResId() = R.layout.item_url_view

	override fun createTransition(fromKey: Any?, toKey: Any, direction: Direction) = ChangeBounds().setDuration(200)

	override fun createComponent(parent: MainComponent) = parent.plus(ItemUrlModule(item))

	@Subcomponent(modules = arrayOf(ItemUrlModule::class))
	interface ItemUrlComponent {
		fun inject(view: ItemUrlView)

		fun plus(shareScreen: ItemUrlShareModule)
	}

	@Module
	class ItemUrlModule(private val item: Item) {
		@Provides
		fun urlItem(): Item = item
	}
}
