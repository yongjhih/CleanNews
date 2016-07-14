package clean.news.presentation.model.item

import clean.news.app.usecase.item.GetChildren
import clean.news.core.entity.Item
import clean.news.presentation.inject.ScreenScope
import clean.news.presentation.model.item.ItemDetailViewModel.Action.GoBack
import clean.news.presentation.model.item.ItemDetailViewModel.Action.GoToUrl
import clean.news.presentation.model.item.ItemDetailViewModel.Action.Share
import clean.news.presentation.model.item.ItemDetailViewModel.Action.ShowChildren
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationFactory.ItemDetailScreen
import clean.news.presentation.navigation.NavigationService
import redux.Dispatcher
import redux.Middleware
import redux.Reducer
import redux.Store
import redux.logger.Logger
import redux.logger.Logger.Event
import redux.logger.LoggerMiddleware
import redux.observable.Epic
import redux.observable.EpicMiddleware
import rx.Observable
import rx.Scheduler
import javax.inject.Inject

@ScreenScope(ItemDetailScreen::class)
class ItemDetailViewModel @Inject constructor(
		private val observeScheduler: Scheduler,
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		private val getChildren: GetChildren,
		item: Item) {

	// State

	data class State(
			val item: Item,
			val children: List<Item>,
			val loading: Boolean)

	private val initialState = State(item, listOf(item), false)

	// Actions

	sealed class Action {
		class GetChildren(val item: Item) : Action()
		class ShowChildren(val children: List<Item>) : Action()
		class GoBack() : Action()
		class GoToUrl() : Action()
		class Share() : Action()
	}

	// Reducer

	private val reducer = object : Reducer<State> {
		override fun reduce(state: State, action: Any): State {
			return when (action) {
				is ShowChildren -> state.copy(children = action.children)
				else -> state
			}
		}
	}

	// Middleware

	private val logger = object : Logger<State> {
		override fun log(event: Event, action: Any, state: State) {

		}
	}
	private val epic = object : Epic<State> {
		override fun map(actions: Observable<out Any>, store: Store<State>): Observable<out Any> {
			return actions.ofType(Action.GetChildren::class.java)
					.flatMap {
						getChildren.execute(GetChildren.Request(it.item))
								.observeOn(observeScheduler)
					}
					.map { Action.ShowChildren(it.items) }
		}
	}

	private val loggerMiddleware = LoggerMiddleware.create(logger)
	private val epicMiddleware = EpicMiddleware.create(epic)
	private val navigationMiddleware = object : Middleware<State> {
		override fun dispatch(store: Store<State>, action: Any, next: Dispatcher): Any {
			when (action) {
				is GoBack -> navService.goBack()
				is GoToUrl -> navService.goTo(navFactory.url(item))
				is Share -> navService.goTo(navFactory.shareDetail(item))
			}
			return action
		}

	}

	// Store

	val store = Store.create(
			reducer,
			initialState,
			Middleware.apply(
					loggerMiddleware,
					epicMiddleware,
					navigationMiddleware
			)
	)

	init {
		store.dispatch(Action.GetChildren(item))
	}

}
