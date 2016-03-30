package clean.news.ui.item.detail

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import clean.news.R
import clean.news.adapter.CommentAdapter
import clean.news.flow.ComponentService
import clean.news.presentation.model.item.ItemDetailViewModel
import clean.news.ui.item.detail.ItemDetailScreen.ItemDetailComponent
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemDetailView : RelativeLayout {
	@Inject
	lateinit var model: ItemDetailViewModel

	private val toolbar: Toolbar by bindView(R.id.toolbar)
	private val titleTextView: TextView by bindView(R.id.title_text_view)
	private val commentRecyclerView: RecyclerView by bindView(R.id.comment_recycler_view)

	private val commentAdapter: CommentAdapter
	private val subscriptions = CompositeSubscription()

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		ComponentService.getService<ItemDetailComponent>(context)?.inject(this)
		commentAdapter = CommentAdapter(context)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		model.item.subscribe { titleTextView.text = it.title }.apply { subscriptions.add(this) }
		model.comments
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe { commentAdapter.setItems(it) }
				.apply { subscriptions.add(this) }
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		super.onDetachedFromWindow()
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
		toolbar.setNavigationOnClickListener { model.backClicks.onNext(null) }

		commentRecyclerView.layoutManager = LinearLayoutManager(context)
		commentRecyclerView.adapter = commentAdapter
	}
}
