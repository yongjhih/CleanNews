package clean.news.ui.item.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import clean.news.adapter.ItemAdapter
import clean.news.app.util.addTo
import clean.news.core.entity.Item
import clean.news.flow.ComponentService
import clean.news.presentation.model.item.ItemListViewModel
import clean.news.presentation.model.item.ItemListViewModel.Sources
import clean.news.ui.item.list.ItemListScreen.ItemListModule
import clean.news.ui.main.MainScreen.MainComponent
import flow.Flow
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemListView : RecyclerView {
	@Inject
	lateinit var model: ItemListViewModel

	private val adapter: ItemAdapter

	private val itemUrlClicks = PublishSubject.create<Item>()
	private val itemDetailClicks = PublishSubject.create<Item>()
	private val subscriptions = CompositeSubscription()

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		layoutManager = LinearLayoutManager(context)
		adapter = ItemAdapter(context)
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		adapter.itemUrlClickListener = { itemUrlClicks.onNext(it) }
		adapter.itemDetailClickListener = { itemDetailClicks.onNext(it) }

		setAdapter(adapter)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()

		val sinks = model.setUp(Sources(
				itemUrlClicks,
				itemDetailClicks
		))

		sinks.items
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe { adapter.setItems(it) }
				.addTo(subscriptions)
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		model.tearDown()
		super.onDetachedFromWindow()
	}

	fun inject(module: ItemListModule) {
		Flow.getService<MainComponent>(ComponentService.NAME, context)
				?.plus(module)
				?.inject(this)
	}
}
