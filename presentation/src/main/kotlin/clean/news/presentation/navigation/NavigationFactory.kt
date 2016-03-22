package clean.news.presentation.navigation

interface NavigationFactory {
	interface MainKey

	interface ItemListKey

	interface ItemDetailKey

	interface UrlKey

	fun mainKey(): MainKey

	fun itemList(): ItemListKey

	fun itemDetail(id: Long): ItemDetailKey

	fun url(url: String): UrlKey
}
