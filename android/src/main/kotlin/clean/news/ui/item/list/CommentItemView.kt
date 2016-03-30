package clean.news.ui.item.list

import android.content.Context
import android.text.Html
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import clean.news.R
import clean.news.adapter.Bindable
import clean.news.core.entity.Item
import com.squareup.phrase.Phrase

class CommentItemView : RelativeLayout, Bindable<Item> {
	private val indentView: View by bindView(R.id.indent_view)
	private val indentMarkerView: View by bindView(R.id.indent_marker_view)
	private val textView: TextView by bindView(R.id.text_view)
	private val bylineTextView: TextView by bindView(R.id.byline_text_view)
	private val collapseButton: View by bindView(R.id.collapse_button)
	private val dividerView: View by bindView(R.id.divider_view)

	private val indentWidth: Int
	private val indentColors: IntArray

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		indentWidth = resources.getDimensionPixelSize(R.dimen.comment_indent_width)
		val colors = resources.obtainTypedArray(R.array.comment_indent_colors)
		indentColors = (0..colors.length() - 1).map { colors.getColor(it, 0) }.toIntArray()
		colors.recycle()
	}

	override fun bind(item: Item) {
		val level = item.level
		val indentTotalWidth = indentWidth * level
		indentView.layoutParams.width = indentTotalWidth
		indentView.requestLayout()

		if (level > 0) {
			indentMarkerView.setBackgroundColor(indentColors[indentColors.size % level])
			indentMarkerView.visibility = VISIBLE
		}
		else {
			indentMarkerView.visibility = GONE
		}

		val timeText = DateUtils.getRelativeTimeSpanString(item.time.time)
		bylineTextView.text = Phrase.from(this, R.string.comment_byline_template)
				.put("by", item.by)
				.put("time", timeText)
				.format()

		textView.text = item.text?.let { Html.fromHtml(item.text) }
	}

	fun setCollapseClickListener(listener: () -> Any?) {
		collapseButton.setOnClickListener { listener.invoke() }
	}

	fun setCollapsed(collapsed: Boolean) {
		textView.visibility = if (collapsed) GONE else VISIBLE
	}

	fun setVisible(visible: Boolean) {
		bylineTextView.visibility = if (visible) VISIBLE else GONE
		textView.visibility = if (visible) VISIBLE else GONE
		dividerView.visibility = if (visible) VISIBLE else GONE
	}

	override fun bindType(): Class<Item> {
		return Item::class.java
	}
}
