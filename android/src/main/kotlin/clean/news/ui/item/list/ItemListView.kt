package clean.news.ui.item.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import clean.news.adapter.ItemAdapter
import clean.news.app.util.addTo
import clean.news.core.entity.Item
import clean.news.flow.service.DaggerService
import clean.news.presentation.collections.streamMapOf
import clean.news.presentation.model.item.ItemListViewModel
import clean.news.presentation.model.item.ItemListViewModel.Sinks
import clean.news.presentation.model.item.ItemListViewModel.Sources
import clean.news.ui.item.list.ItemListScreen.ItemListModule
import clean.news.ui.main.MainKey.MainComponent
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

		val sinks = model.attach(streamMapOf(
				Sources.ITEM_URL_CLICKS to itemUrlClicks,
				Sources.ITEM_DETAIL_CLICKS to itemDetailClicks
		))

		Sinks.ITEMS<List<Item>>(sinks)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe { adapter.setItems(it) }
				.addTo(subscriptions)
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		model.detach()
		super.onDetachedFromWindow()
	}

	fun inject(module: ItemListModule) {
		Flow.getService<MainComponent>(DaggerService.NAME, context)
				?.plus(module)
				?.inject(this)
	}
}
