package clean.news.ui.item.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import clean.news.adapter.ItemAdapter
import clean.news.flow.ComponentService
import clean.news.presentation.model.item.ItemListViewModel
import clean.news.ui.item.list.ItemListScreen.ItemListModule
import clean.news.ui.main.MainScreen.MainComponent
import flow.Flow
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemListView : RecyclerView {
	@Inject
	lateinit var model: ItemListViewModel

	private val adapter: ItemAdapter
	private val subscriptions = CompositeSubscription()

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		Flow.getService<MainComponent>(ComponentService.NAME, context)
				?.plus(ItemListModule())
				?.inject(this)

		layoutManager = LinearLayoutManager(context)
		adapter = ItemAdapter(context)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()

		model.items
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe { adapter.setItems(it) }
				.apply { subscriptions.add(this) }
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		super.onDetachedFromWindow()
	}

	override fun onFinishInflate() {
		super.onFinishInflate()
		setAdapter(adapter)
	}
}
