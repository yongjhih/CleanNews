package clean.news.ui.item.url

import android.content.Intent
import clean.news.core.entity.Item
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.navigation.AppNavigationService.WithActivity
import clean.news.presentation.navigation.NavigationFactory.ItemShareUrlScreen
import clean.news.ui.item.url.ItemUrlKey.ItemUrlComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import flow.TreeKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class ItemUrlShareKey(val item: Item) : ClassKey(),
		TreeKey,
		ItemShareUrlScreen,
		WithActivity,
		WithComponent,
		PaperParcelable {

	override fun getParentKey() = ItemUrlKey(item)

	override fun createComponent(parent: Any): Any {
		if (parent !is ItemUrlComponent) {
			throw IllegalArgumentException()
		}
		return parent.plus(ItemUrlShareModule(item))
	}

	override fun createIntent(): Intent {
		val shareIntent = Intent(Intent.ACTION_SEND)
		shareIntent.type = "text/plain"
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "${item.title}")
		shareIntent.putExtra(Intent.EXTRA_TEXT, "${item.title} | ${item.url}")
		return Intent.createChooser(shareIntent, "Share via...")
	}

	@Subcomponent (modules = arrayOf(ItemUrlShareModule::class))
	interface ItemUrlShareComponent {
	}

	@Module
	class ItemUrlShareModule(private val item: Item) {
		@Provides
		fun sharedItem(): Item = item
	}

}
