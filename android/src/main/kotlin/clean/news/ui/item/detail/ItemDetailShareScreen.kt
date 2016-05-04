package clean.news.ui.item.detail

import android.content.Intent
import clean.news.core.entity.Item
import clean.news.data.retrofit.threadUrl
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.navigation.AppNavigationService.WithActivity
import clean.news.presentation.navigation.NavigationFactory.ItemShareDetailKey
import clean.news.ui.item.detail.ItemDetailScreen.ItemDetailComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import flow.TreeKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class ItemDetailShareScreen(val item: Item) : ClassKey(),
		TreeKey,
		ItemShareDetailKey,
		WithActivity,
		WithComponent,
		PaperParcelable {

	override fun getParentKey() = ItemDetailScreen(item)

	override fun createComponent(parent: Any): Any {
		if (parent !is ItemDetailComponent) {
			throw IllegalArgumentException()
		}
		return parent.plus(ItemDetailShareModule(item))
	}

	override fun createIntent(): Intent {
		val shareIntent = Intent(Intent.ACTION_SEND)
		shareIntent.type = "text/plain"
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, "${item.title}")
		shareIntent.putExtra(Intent.EXTRA_TEXT, "${item.title} | ${item.threadUrl()}")
		return Intent.createChooser(shareIntent, "Share via...")
	}

	@Subcomponent (modules = arrayOf(ItemDetailShareModule::class))
	interface ItemDetailShareComponent {
	}

	@Module
	class ItemDetailShareModule(private val item: Item) {
		@Provides
		fun sharedItem(): Item = item
	}

}
