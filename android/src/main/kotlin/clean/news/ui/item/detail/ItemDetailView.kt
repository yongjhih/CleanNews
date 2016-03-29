package clean.news.ui.item.detail

import android.content.Context
import android.support.v7.widget.Toolbar
import android.text.format.DateUtils
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import clean.news.R
import clean.news.core.entity.Item
import clean.news.flow.ComponentService
import clean.news.presentation.model.item.ItemDetailViewModel
import clean.news.ui.item.detail.ItemDetailScreen.ItemDetailComponent
import com.squareup.phrase.Phrase
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemDetailView : RelativeLayout {
	@Inject
	lateinit var model: ItemDetailViewModel

	private val toolbar: Toolbar by bindView(R.id.toolbar)
	private val titleTextView: TextView by bindView(R.id.title_text_view)
	private val bylineTextView: TextView by bindView(R.id.byline_text_view)

	private val subscriptions = CompositeSubscription()

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		ComponentService.getService<ItemDetailComponent>(context)?.inject(this)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		model.item.subscribe(itemSubscriber).apply { subscriptions.add(this) }
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		super.onDetachedFromWindow()
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
		toolbar.setNavigationOnClickListener { model.backClicks.onNext(null) }
	}

	private val itemSubscriber = { item: Item ->
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
	}
}
