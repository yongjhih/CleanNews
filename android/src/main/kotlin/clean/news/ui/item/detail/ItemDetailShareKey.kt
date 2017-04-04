package clean.news.ui.item.detail

import android.content.Intent
import clean.news.core.entity.Item
import clean.news.data.retrofit.threadUrl
import clean.news.flow.service.DaggerService.WithComponent
import clean.news.navigation.AppNavigationService.WithActivity
import clean.news.presentation.navigation.NavigationFactory.ItemShareDetailScreen
import clean.news.ui.item.detail.ItemDetailKey.ItemDetailComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import flow.TreeKey
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

class ItemDetailShareKey(val item: Item) : ClassKey(),
		TreeKey,
		ItemShareDetailScreen,
		WithActivity,
		WithComponent {

	override fun getParentKey() = ItemDetailKey(item)

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
