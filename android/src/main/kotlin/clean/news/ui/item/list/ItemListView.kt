package clean.news.ui.item.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import clean.news.adapter.ItemAdapter
import clean.news.flow.service.DaggerService
import clean.news.presentation.model.item.ItemListViewModel
import clean.news.presentation.model.item.ItemListViewModel.Action
import clean.news.ui.item.list.ItemListScreen.ItemListModule
import clean.news.ui.main.MainKey.MainComponent
import flow.Flow
import redux.Store
import redux.Store.Subscription
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemListView : RecyclerView, Store.Subscriber {

	@Inject
	lateinit var model: ItemListViewModel

	private val adapter: ItemAdapter

	private val subscriptions = CompositeSubscription()
	private lateinit var subscription: Subscription

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		layoutManager = LinearLayoutManager(context)
		adapter = ItemAdapter(context)
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		adapter.itemUrlClickListener = { model.store.dispatch(Action.GoToUrl(it)) }
		adapter.itemDetailClickListener = { model.store.dispatch(Action.GoToDetail(it)) }

		setAdapter(adapter)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		subscription = model.store.subscribe(this)
		onStateChanged()
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		subscription.unsubscribe()
		super.onDetachedFromWindow()
	}

	override fun onStateChanged() {
		val state = model.store.getState()
		adapter.setItems(state.items)
	}

	fun inject(module: ItemListModule) {
		Flow.getService<MainComponent>(DaggerService.NAME, context)
				?.plus(module)
				?.inject(this)
	}
}
