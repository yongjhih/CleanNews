package clean.news.presentation.model.item

import clean.news.core.entity.Item
import clean.news.presentation.model.item.ItemUrlViewModel.Action.GoBack
import clean.news.presentation.model.item.ItemUrlViewModel.Action.GoToDetails
import clean.news.presentation.model.item.ItemUrlViewModel.Action.Share
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import redux.Dispatcher
import redux.Middleware
import redux.Reducer
import redux.Store
import redux.logger.Logger
import redux.logger.Logger.Event
import redux.logger.LoggerMiddleware
import javax.inject.Inject

class ItemUrlViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		val item: Item) {

	// State

	data class State(val item: Item)

	// Actions

	sealed class Action {
		class GoBack() : Action()
		class GoToDetails() : Action()
		class Share() : Action()
	}

	// Reducer

	private val reducer = object : Reducer<State> {
		override fun reduce(state: State, action: Any): State {
			return state
		}
	}

	//

	private val logger = object : Logger<State> {
		override fun log(event: Event, action: Any, state: State) {
		}
	}

	private val loggerMiddleware = LoggerMiddleware.create(logger)
	private val navigationMiddleware = object : Middleware<State> {
		override fun dispatch(store: Store<State>, action: Any, next: Dispatcher): Any {
			when (action) {
				is GoBack -> navService.goBack()
				is GoToDetails -> navService.goTo(navFactory.detail(item))
				is Share -> navService.goTo(navFactory.shareDetail(item))
			}
			return action
		}

	}

	// Store

	val store = Store.create(
			reducer,
			State(item),
			Middleware.apply(
					loggerMiddleware,
					navigationMiddleware
			)
	)

}
