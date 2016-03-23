package clean.news.flow

interface WithComponent<T> {
	fun createComponent(parent: T): Any
}
