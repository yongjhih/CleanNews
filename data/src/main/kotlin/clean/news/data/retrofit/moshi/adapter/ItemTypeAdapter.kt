package clean.news.data.retrofit.moshi.adapter

import clean.news.core.entity.Item
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class ItemTypeAdapter : JsonAdapter<Item.Type>() {
	@FromJson
	override fun fromJson(reader: JsonReader): Item.Type {
		val type = reader.nextString()
		return Item.Type.valueOf(type.toUpperCase())
	}

	@ToJson
	override fun toJson(writer: JsonWriter, value: Item.Type) {
		writer.value(value.name.toLowerCase())
	}
}
