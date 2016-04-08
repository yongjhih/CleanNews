package clean.news.presentation.model

import clean.news.presentation.model.Model.Sinks
import clean.news.presentation.model.Model.Sources

interface Model<I : Sources, O : Sinks> {
	fun setUp(sources: I): O

	fun tearDown()

	interface Sources

	interface Sinks
}
