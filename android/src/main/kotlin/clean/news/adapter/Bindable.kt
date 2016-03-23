package clean.news.adapter

interface Bindable<T> {
	fun bind(data: T)

	fun bindType(): Class<T> // TODO: can this be avoided? Look in ItemAdapter to see why it exists.
}
