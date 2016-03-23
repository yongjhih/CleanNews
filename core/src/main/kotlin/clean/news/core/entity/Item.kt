package clean.news.core.entity

import java.util.Date

data class Item(
		val id: Long,
		val deleted: Boolean,
		val type: Type,
		val by: String,
		val time: Date,
		val text: String,
		val dead: Boolean,
		val parent: Long,
		val kids: List<Long>,
		val url: String?,
		val score: Int?,
		val title: String?,
		val parts: List<Long>,
		val descendants: Int) {

	enum class Type {
		JOB, STORY, COMMENT, POLL, POLLOPT
	}

	enum class ListType {
		TOP, NEW, SHOW, ASK, JOBS
	}
}
