package clean.news.presentation.navigation

import clean.news.core.entity.Item

interface NavigationFactory {
	interface MainKey

	interface ItemDetailKey

	interface ItemUrlKey

	interface ItemShareDetailKey

	interface ItemShareUrlKey

	fun main(): MainKey

	fun itemDetail(item: Item): ItemDetailKey

	fun url(item: Item): ItemUrlKey

	fun shareDetail(item: Item): ItemShareDetailKey

	fun shareUrl(item: Item): ItemShareUrlKey
}
