package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetChildren
import clean.news.core.entity.Item
import clean.news.presentation.inject.ScreenScope
import clean.news.presentation.model.StoreModel
import clean.news.presentation.model.item.ItemDetailViewModel.Action.GoBack
import clean.news.presentation.model.item.ItemDetailViewModel.Action.GoToUrl
import clean.news.presentation.model.item.ItemDetailViewModel.Action.Share
import clean.news.presentation.model.item.ItemDetailViewModel.Action.ShowChildren
import clean.news.presentation.model.item.ItemDetailViewModel.State
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationFactory.ItemDetailScreen
import clean.news.presentation.navigation.NavigationService
import io.reactivex.Observable
import io.reactivex.Scheduler
import redux.api.Dispatcher
import redux.api.Reducer
import redux.api.Store
import redux.api.enhancer.Middleware
import redux.observable.Epic
import javax.inject.Inject

@ScreenScope(ItemDetailScreen::class)
class ItemDetailViewModel @Inject constructor(
		private val observeScheduler: Scheduler,
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val getChildren: GetChildren,
		private val item: Item) : StoreModel<State>() {

	init {
		dispatch(Action.GetChildren(item))
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// State

	data class State(
			val item: Item,
			val children: List<Item>,
			val loading: Boolean)

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Actions

	sealed class Action {
		class GetChildren(val item: Item) : Action()
		class ShowChildren(val children: List<Item>) : Action()
		class GoBack() : Action()
		class GoToUrl() : Action()
		class Share() : Action()
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Reducer

	private fun reducer() = Reducer { state: State, action: Any ->
		when (action) {
			is ShowChildren -> state.copy(children = action.children)
			else -> state
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Epic

	private fun epic() = Epic { actions: Observable<out Any>, store: Store<State> ->
		actions.ofType(Action.GetChildren::class.java)
				.flatMap {
					getChildren.execute(GetChildren.Request(it.item))
							.observeOn(observeScheduler)
				}
				.map { Action.ShowChildren(it.items) }
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Nav

	private fun navigationMiddleware() = Middleware { store: Store<State>, next: Dispatcher, action: Any ->
		val result = next.dispatch(action)
		when (action) {
			is GoBack -> navService.goBack()
			is GoToUrl -> navService.goTo(navFactory.url(item))
			is Share -> navService.goTo(navFactory.shareDetail(item))
		}
		result
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Overrides

	override fun createStore(): Store<State> {
		return redux.createStore(
				reducer(),
				State(item, listOf(item), false),
				redux.applyMiddleware(
						navigationMiddleware(),
						redux.observable.createEpicMiddleware(epic())
				))
	}

}
