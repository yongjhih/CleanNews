package clean.news.ui.item.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import clean.news.adapter.ItemAdapter
import clean.news.app.util.addTo
import clean.news.flow.service.DaggerService
import clean.news.presentation.model.item.ItemListViewModel
import clean.news.presentation.model.item.ItemListViewModel.Action
import clean.news.ui.item.list.ItemListScreen.ItemListModule
import clean.news.ui.main.MainKey.MainComponent
import flow.Flow
import redux.asObservable
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class ItemListView : RecyclerView {

	@Inject
	lateinit var model: ItemListViewModel

	private val adapter: ItemAdapter

	private val subscriptions = CompositeSubscription()

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		layoutManager = LinearLayoutManager(context)
		adapter = ItemAdapter(context)
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		adapter.itemUrlClickListener = { model.dispatch(Action.GoToUrl(it)) }
		adapter.itemDetailClickListener = { model.dispatch(Action.GoToDetail(it)) }

		setAdapter(adapter)
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()

		val stateChanges = model.asObservable()
				.startWith(model.getState())
				.publish()

		stateChanges
				.map { it.items }
				.distinctUntilChanged()
				.subscribe { adapter.setItems(it) }
				.addTo(subscriptions)

		stateChanges
				.connect()
				.addTo(subscriptions)
	}

	override fun onDetachedFromWindow() {
		subscriptions.unsubscribe()
		super.onDetachedFromWindow()
	}

	fun inject(module: ItemListModule) {
		Flow.getService<MainComponent>(DaggerService.NAME, context)
				?.plus(module)
				?.inject(this)
	}
}
