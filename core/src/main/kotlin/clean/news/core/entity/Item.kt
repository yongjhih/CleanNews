package clean.news.core.entity

import java.util.Date

data class Item(
		val id: Long,
		val deleted: Boolean?,
		val type: Type,
		val by: String?,
		val time: Date,
		val text: String?,
		val dead: Boolean?,
		val parent: Long?,
		val kids: List<Long>?,
		val url: String?,
		val score: Int?,
		val title: String?,
		val parts: List<Long>?,
		val descendants: Int?,
		val level: Int = 0) {

	enum class Type(val canComment: Boolean) {
		JOB(false), STORY(true), COMMENT(true), POLL(true), POLLOPT(true), NONE(false)
	}

	enum class ListType {
		TOP, NEW, SHOW, ASK, JOBS
	}

	companion object {
		val EMPTY = Item(-1, null, Type.NONE, null, Date(0), null, null, null, null, null, null, null, null, null, -1)
	}

	fun threadUrl(): String = "https://news.ycombinator.com/item?id=${this.id}"
}
