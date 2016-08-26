package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetItemsByListType
import clean.news.core.entity.Item
import clean.news.presentation.model.StoreModel
import clean.news.presentation.model.item.ItemListViewModel.Action.GoToDetail
import clean.news.presentation.model.item.ItemListViewModel.Action.GoToUrl
import clean.news.presentation.model.item.ItemListViewModel.Action.ShowItems
import clean.news.presentation.model.item.ItemListViewModel.State
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import redux.Dispatcher
import redux.Middleware
import redux.Reducer
import redux.Store
import redux.observable.Epic
import redux.observable.EpicMiddleware
import rx.Observable
import rx.Scheduler
import javax.inject.Inject

class ItemListViewModel @Inject constructor(
		private val observeScheduler: Scheduler,
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val listType: Item.ListType,
		private val getItemsByListType: GetItemsByListType) : StoreModel<State>() {

	// State

	data class State(
			val items: List<Item>,
			val loading: Boolean)

	// Actions

	sealed class Action {
		class GetItems() : Action()
		class ShowItems(val items: List<Item>) : Action()
		class GoToDetail(val item: Item) : Action()
		class GoToUrl(val item: Item) : Action()
	}

	override fun createStore(): Store<State> {
		// Reducer

		val reducer = object : Reducer<State> {
			override fun reduce(state: State, action: Any): State {
				return when (action) {
					is ShowItems -> state.copy(items = action.items)
					else -> state
				}
			}
		}

		// Middleware

		val epic = object : Epic<State> {
			override fun map(actions: Observable<out Any>, store: Store<State>): Observable<out Any> {
				return actions.ofType(Action.GetItems::class.java)
						.flatMap {
							getItemsByListType.execute(GetItemsByListType.Request(listType))
									.observeOn(observeScheduler)
						}
						.map { Action.ShowItems(it.items) }
			}
		}

		val epicMiddleware = EpicMiddleware.create(epic)
		val navigationMiddleware = object : Middleware<State> {
			override fun dispatch(store: Store<State>, action: Any, next: Dispatcher): Any {
				when (action) {
					is GoToUrl -> navService.goTo(navFactory.url(action.item))
					is GoToDetail -> navService.goTo(navFactory.detail(action.item))
				}
				return action
			}

		}

		return Store.create(
				reducer,
				State(
						emptyList(),
						false
				),
				Middleware.apply(
						epicMiddleware,
						navigationMiddleware
				)
		)
	}

	init {
		dispatch(Action.GetItems())
	}
}
