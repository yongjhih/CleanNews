package clean.news.ui.item.detail

import android.transition.ChangeBounds
import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.keychanger.SceneKeyChanger.WithLayout
import clean.news.flow.keychanger.SceneKeyChanger.WithTransition
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.presentation.inject.ScreenScope
import clean.news.presentation.navigation.NavigationFactory.ItemDetailScreen
import clean.news.ui.item.detail.ItemDetailShareKey.ItemDetailShareComponent
import clean.news.ui.item.detail.ItemDetailShareKey.ItemDetailShareModule
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
class ItemDetailKey(val item: Item) : ClassKey(),
		TreeKey,
		ItemDetailScreen,
		WithLayout,
		WithTransition,
		WithComponent,
		PaperParcelable {

	override fun getParentKey() = MainKey()

	override fun getLayoutResId() = R.layout.item_detail_view

	override fun createTransition(fromKey: Any?, toKey: Any, direction: Direction) = ChangeBounds().setDuration(200)

	override fun createComponent(parent: Any): Any {
		if (parent !is MainComponent) {
			throw IllegalArgumentException()
		}
		return parent.plus(ItemDetailModule(item))
	}

	@ScreenScope(ItemDetailScreen::class)
	@Subcomponent(modules = arrayOf(ItemDetailModule::class))
	interface ItemDetailComponent {
		fun inject(view: ItemDetailView)

		fun plus(module: ItemDetailShareModule): ItemDetailShareComponent
	}

	@Module
	class ItemDetailModule(private val item: Item) {
		@Provides
		fun detailItem(): Item = item
	}

}
