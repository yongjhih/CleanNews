package clean.news.ui.item.detail

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.os.ParcelableCompat
import android.support.v4.os.ParcelableCompatCreatorCallbacks
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import clean.news.R
import clean.news.adapter.ItemDetailAdapter
import clean.news.app.util.addTo
import clean.news.flow.ComponentService
import clean.news.presentation.model.item.ItemDetailViewModel
import clean.news.presentation.model.item.ItemDetailViewModel.Sources
import clean.news.ui.item.detail.ItemDetailScreen.ItemDetailComponent
import com.jakewharton.rxbinding.support.v7.widget.itemClicks
import com.jakewharton.rxbinding.support.v7.widget.navigationClicks
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemDetailView : RelativeLayout {
	@Inject
	lateinit var model: ItemDetailViewModel

	private val toolbar: Toolbar by bindView(R.id.toolbar)
	private val titleTextView: TextView by bindView(R.id.title_text_view)
	private val commentRecyclerView: RecyclerView by bindView(R.id.comment_recycler_view)

	private val adapter: ItemDetailAdapter
	private val subscriptions = CompositeSubscription()

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		ComponentService.getService<ItemDetailComponent>(context)?.inject(this)
		adapter = ItemDetailAdapter(context)
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
		toolbar.inflateMenu(R.menu.item_detail_view)
		commentRecyclerView.layoutManager = LinearLayoutManager(context)
		commentRecyclerView.adapter = adapter
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		val sinks = model.setUp(Sources(
				toolbar.navigationClicks(),
				Observable.empty(),
				toolbar.itemClicks().filter {it.itemId == R.id.item_share }
		))

		sinks.item
				.subscribe { titleTextView.text = it.title }
				.addTo(subscriptions)

		sinks.children
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						{
							adapter.setItems(it)
							adapter.setLoading(true)
						},
						{},
						{
							adapter.setLoading(false)
						}
				)
				.addTo(subscriptions)
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		model.tearDown()
		super.onDetachedFromWindow()
	}

	override fun onSaveInstanceState(): Parcelable? {
		val superState = super.onSaveInstanceState()
		val savedState = SavedState(superState)
		val collapsedIds = adapter.getCollapsedIds()
		savedState.collapsedCount = collapsedIds.size
		savedState.collapsedIds = collapsedIds
		return savedState
	}

	override fun onRestoreInstanceState(state: Parcelable?) {
		val savedState = state as SavedState
		super.onRestoreInstanceState(state.superState)
		adapter.setCollapsedIds(savedState.collapsedIds)
	}

	class SavedState : BaseSavedState {
		var collapsedCount = 0
		var collapsedIds = LongArray(0)

		constructor(source: Parcel, loader: ClassLoader) : super(source) {
			source.readInt()
			collapsedIds = LongArray(collapsedCount)
			source.readLongArray(collapsedIds)
		}

		constructor(superState: Parcelable) : super(superState) {
		}

		override fun writeToParcel(out: Parcel, flags: Int) {
			super.writeToParcel(out, flags)
			out.writeInt(collapsedCount)
			out.writeLongArray(collapsedIds)
		}

		companion object {
			@JvmField
			val CREATOR = ParcelableCompat.newCreator(object : ParcelableCompatCreatorCallbacks<SavedState> {
				override fun createFromParcel(source: Parcel, loader: ClassLoader): SavedState? {
					return SavedState(source, loader)
				}

				override fun newArray(size: Int): Array<SavedState?> {
					return Array(size) { null }
				}
			})
		}
	}
}
