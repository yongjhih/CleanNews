package clean.news.ui.item.list

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import clean.news.R
import clean.news.adapter.Binder
import clean.news.core.entity.Item

class ItemView : RelativeLayout, Binder<Item> {
	private val titleTextView: TextView by bindView(R.id.title_text_view)

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
	}

	override fun bind(data: Item) {
		titleTextView.text = data.title
	}

	override fun bindType(): Class<Item> {
		return Item::class.java
	}
}
