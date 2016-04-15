package clean.news.ui.item.share

import android.content.Intent
import clean.news.core.entity.Item
import clean.news.data.retrofit.threadUrl
import clean.news.flow.WithComponent
import clean.news.flow.WithIntent
import clean.news.presentation.navigation.NavigationFactory.ItemShareKey
import clean.news.ui.item.detail.ItemDetailScreen
import clean.news.ui.item.detail.ItemDetailScreen.ItemDetailComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import flow.TreeKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class ItemShareIntent(val item: Item) : ClassKey(),
		TreeKey,
		ItemShareKey,
		WithIntent,
		WithComponent<ItemDetailComponent>,
		PaperParcelable {

	override fun getParentKey() = ItemDetailScreen(item)

	override fun createComponent(parent: ItemDetailComponent) = parent.plus(ItemDetailShareModule(item))

	override fun intent(): Intent {
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
