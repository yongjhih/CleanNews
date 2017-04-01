package clean.news.ui.item.url

import android.transition.ChangeBounds
import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.keychanger.SceneKeyChanger.WithLayout
import clean.news.flow.keychanger.SceneKeyChanger.WithTransition
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.presentation.inject.ScreenScope
import clean.news.presentation.navigation.NavigationFactory.ItemUrlScreen
import clean.news.ui.item.url.ItemUrlShareKey.ItemUrlShareModule
import clean.news.ui.main.MainKey
import clean.news.ui.main.MainKey.MainComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import flow.Direction
import flow.TreeKey
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
class ItemUrlKey(val item: Item) : ClassKey(),
		TreeKey,
		ItemUrlScreen,
		WithLayout,
		WithTransition,
		WithComponent,
		PaperParcelable {

	override fun getParentKey() = MainKey()

	override fun getLayoutResId() = R.layout.item_url_view

	override fun createTransition(fromKey: Any?, toKey: Any, direction: Direction) = ChangeBounds().setDuration(200)

	override fun createComponent(parent: Any): Any {
		if (parent !is MainComponent) {
			throw IllegalArgumentException()
		}
		return parent.plus(ItemUrlModule(item))
	}

	@ScreenScope(ItemUrlScreen::class)
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
