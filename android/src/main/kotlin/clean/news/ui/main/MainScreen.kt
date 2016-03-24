package clean.news.ui.main

import clean.news.R
import clean.news.flow.WithComponent
import clean.news.flow.WithLayout
import clean.news.inject.component.ApplicationComponent
import clean.news.presentation.inject.ActivityScope
import clean.news.presentation.navigation.NavigationFactory.MainKey
import clean.news.ui.item.detail.ItemDetailScreen.ItemDetailComponent
import clean.news.ui.item.detail.ItemDetailScreen.ItemDetailModule
import clean.news.ui.item.list.ItemListScreen.ItemListComponent
import clean.news.ui.item.list.ItemListScreen.ItemListModule
import clean.news.ui.item.url.ItemUrlScreen.ItemUrlComponent
import clean.news.ui.item.url.ItemUrlScreen.ItemUrlModule
import dagger.Subcomponent
import flow.ClassKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class MainScreen : ClassKey(), MainKey, WithLayout, WithComponent<ApplicationComponent>, PaperParcelable {
	override fun getLayoutResId() = R.layout.main_view

	override fun createComponent(parent: ApplicationComponent) = parent.mainComponent()

	@ActivityScope
	@Subcomponent
	interface MainComponent {
		fun inject(mainView: MainView)

		fun plus(itemListModule: ItemListModule): ItemListComponent

		fun plus(itemDetailModule: ItemDetailModule): ItemDetailComponent

		fun plus(itemUrlModule: ItemUrlModule): ItemUrlComponent
	}
}
