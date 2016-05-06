package clean.news.ui.main

import android.transition.ChangeBounds
import clean.news.R
import clean.news.flow.keychanger.SceneKeyChanger.WithLayout
import clean.news.flow.keychanger.SceneKeyChanger.WithTransition
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.inject.component.ApplicationComponent
import clean.news.presentation.inject.ActivityScope
import clean.news.presentation.navigation.NavigationFactory.MainScreen
import clean.news.ui.item.detail.ItemDetailKey.ItemDetailComponent
import clean.news.ui.item.detail.ItemDetailKey.ItemDetailModule
import clean.news.ui.item.list.ItemListScreen.ItemListComponent
import clean.news.ui.item.list.ItemListScreen.ItemListModule
import clean.news.ui.item.url.ItemUrlKey.ItemUrlComponent
import clean.news.ui.item.url.ItemUrlKey.ItemUrlModule
import dagger.Subcomponent
import flow.ClassKey
import flow.Direction
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class MainKey : ClassKey(),
		MainScreen,
		WithLayout,
		WithTransition,
		WithComponent,
		PaperParcelable {

	override fun getLayoutResId() = R.layout.main_view

	override fun createTransition(fromKey: Any?, toKey: Any, direction: Direction) = ChangeBounds().setDuration(200)

	override fun createComponent(parent: Any): Any {
		if (parent !is ApplicationComponent) {
			throw IllegalArgumentException()
		}
		return parent.mainComponent()
	}

	@ActivityScope
	@Subcomponent
	interface MainComponent {
		fun inject(mainView: MainView)

		fun plus(itemListModule: ItemListModule): ItemListComponent

		fun plus(itemDetailModule: ItemDetailModule): ItemDetailComponent

		fun plus(itemUrlModule: ItemUrlModule): ItemUrlComponent
	}

}
