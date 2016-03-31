package clean.news.presentation.model

import clean.news.presentation.model.Model.Sinks
import clean.news.presentation.model.Model.Sources

interface Model<I : Sources, O : Sinks> {
	fun onAttach(sources: I): O

	fun onDetach()

	interface Sources

	interface Sinks
}
