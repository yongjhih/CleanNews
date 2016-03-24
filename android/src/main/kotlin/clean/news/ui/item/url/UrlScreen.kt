package clean.news.ui.item.url

import clean.news.presentation.navigation.NavigationFactory.UrlKey
import clean.news.ui.main.MainScreen
import flow.ClassKey
import flow.TreeKey
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
class UrlScreen(val url: String) : ClassKey(), TreeKey, UrlKey, PaperParcelable {
	override fun getParentKey(): Any = MainScreen()
}
