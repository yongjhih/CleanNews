package clean.news.ui.item.url

import android.content.Intent
import clean.news.core.entity.Item
import clean.news.flow.WithComponent
import clean.news.flow.WithIntent
import clean.news.presentation.navigation.NavigationFactory.ItemShareUrlKey
import clean.news.ui.item.url.ItemUrlScreen.ItemUrlComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.ClassKey
import flow.TreeKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class ItemUrlShareScreen(val item: Item) : ClassKey(),
		TreeKey,
		ItemShareUrlKey,
		WithIntent,
		WithComponent<ItemUrlComponent>,
		PaperParcelable {

	override fun getParentKey() = ItemUrlScreen(item)

	override fun createComponent(parent: ItemUrlComponent) = parent.plus(ItemUrlShareModule(item))

	override fun intent(): Intent {
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
