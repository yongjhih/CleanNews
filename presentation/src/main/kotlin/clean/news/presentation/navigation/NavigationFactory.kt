package clean.news.presentation.navigation

import clean.news.core.entity.Item

interface NavigationFactory {
	interface MainScreen

	interface ItemDetailScreen

	interface ItemUrlScreen

	interface ItemShareDetailScreen

	interface ItemShareUrlScreen

	fun main(): MainScreen

	fun detail(item: Item): ItemDetailScreen

	fun url(item: Item): ItemUrlScreen

	fun shareDetail(item: Item): ItemShareDetailScreen

	fun shareUrl(item: Item): ItemShareUrlScreen
}
