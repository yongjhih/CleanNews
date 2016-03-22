package clean.news.navigation

interface NavigationService {
	fun goBack(): Boolean

	fun goTo(newTop: Any)

	fun resetTo(newTop: Any)
}
