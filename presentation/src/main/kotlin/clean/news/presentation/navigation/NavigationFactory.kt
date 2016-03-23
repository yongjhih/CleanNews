package clean.news.presentation.navigation

interface NavigationFactory {
	interface MainKey

	interface ItemDetailKey

	interface UrlKey

	fun main(): MainKey

	fun itemDetail(id: Long): ItemDetailKey

	fun url(url: String): UrlKey
}
