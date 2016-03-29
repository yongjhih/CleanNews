package clean.news.ui.item.list

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import clean.news.R
import clean.news.adapter.Bindable
import clean.news.core.entity.Item
import com.squareup.phrase.Phrase

class ItemView : RelativeLayout, Bindable<Item> {
	private val titleTextView: TextView by bindView(R.id.title_text_view)
	private val bylineTextView: TextView by bindView(R.id.byline_text_view)
	private val detailsButton: ImageView by bindView(R.id.details_button)

	var urlClickListener: ((Item) -> Any?)? = null
	var detailsClickListener: ((Item) -> Any?)? = null

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
	}

	override fun bind(item: Item) {
		titleTextView.text = item.title

		val timeText = DateUtils.getRelativeTimeSpanString(item.time.time)
		if (item.type != Item.Type.JOB) {
			bylineTextView.text = Phrase.from(resources.getQuantityString(R.plurals.byline_template, item.score!!))
					.put("score", item.score!!)
					.put("by", item.by)
					.put("time", timeText)
					.format()
		}
		else {
			bylineTextView.text = timeText
		}

		detailsButton.setOnClickListener { detailsClickListener?.invoke(item) }

		setOnClickListener {
			if (item.url != null) {
				urlClickListener?.invoke(item)
			}
			else {
				detailsClickListener?.invoke(item)
			}
		}
	}

	override fun bindType(): Class<Item> {
		return Item::class.java
	}
}
