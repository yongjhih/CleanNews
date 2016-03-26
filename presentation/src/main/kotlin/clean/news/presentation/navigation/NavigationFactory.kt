package clean.news.presentation.navigation

import clean.news.core.entity.Item

interface NavigationFactory {
	interface MainKey

	interface ItemDetailKey

	interface UrlKey

	fun main(): MainKey

	fun itemDetail(item: Item): ItemDetailKey

	fun url(url: String): UrlKey
}
