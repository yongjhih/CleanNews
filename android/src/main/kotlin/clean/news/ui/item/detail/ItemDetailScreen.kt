package clean.news.ui.item.detail

import android.transition.ChangeBounds
import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.keychanger.SceneKeyChanger.WithLayout
import clean.news.flow.keychanger.SceneKeyChanger.WithTransition
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.presentation.inject.ClassScope
import clean.news.presentation.model.item.ItemDetailViewModel
import clean.news.presentation.navigation.NavigationFactory.ItemDetailKey
import clean.news.ui.item.detail.ItemDetailShareScreen.ItemDetailShareComponent
import clean.news.ui.item.detail.ItemDetailShareScreen.ItemDetailShareModule
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
class ItemDetailScreen(val item: Item) : ClassKey(),
		TreeKey,
		ItemDetailKey,
		WithLayout,
		WithTransition,
		WithComponent,
		PaperParcelable {

	override fun getParentKey() = MainScreen()

	override fun getLayoutResId() = R.layout.item_detail_view

	override fun createTransition(fromKey: Any?, toKey: Any, direction: Direction) = ChangeBounds().setDuration(200)

	override fun createComponent(parent: Any): Any {
		if (parent !is MainComponent) {
			throw IllegalArgumentException()
		}
		return parent.plus(ItemDetailModule(item))
	}

	@ClassScope(ItemDetailViewModel::class)
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
