package clean.news.data.retrofit.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.util.Date

class UnixTimeAdapter : JsonAdapter<Date>() {
	private val SECOND_IN_MILLIS = 1000

	@FromJson
	override fun fromJson(reader: JsonReader): Date {
		val unixTimestamp = reader.nextLong()
		return Date(unixTimestamp * SECOND_IN_MILLIS)
	}

	@ToJson
	override fun toJson(writer: JsonWriter, value: Date) {
		writer.value(value.time / SECOND_IN_MILLIS)
	}
}
