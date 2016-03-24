package clean.news.ui.item.list

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import clean.news.R
import clean.news.adapter.Bindable
import clean.news.core.entity.Item

class ItemView : RelativeLayout, Bindable<Item> {
	private val titleTextView: TextView by bindView(R.id.title_text_view)
	private val authorTextView: TextView by bindView(R.id.author_text_view)
	private val detailsButton: ImageView by bindView(R.id.details_button)

	var urlClickListener: ((String) -> Any?)? = null
	var detailsClickListener: ((Long) -> Any?)? = null

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
	}

	override fun bind(data: Item) {
		val id = data.id
		val url = data.url

		titleTextView.text = data.title
		authorTextView.text = data.by
		detailsButton.setOnClickListener { detailsClickListener?.invoke(id) }

		setOnClickListener {
			if (url != null) {
				urlClickListener?.invoke(url)
			} else {
				detailsClickListener?.invoke(id)
			}
		}
	}

	override fun bindType(): Class<Item> {
		return Item::class.java
	}
}
