package clean.news.presentation.model.item

import clean.news.core.entity.Item
import clean.news.presentation.model.StoreModel
import clean.news.presentation.model.item.ItemUrlViewModel.Action.GoBack
import clean.news.presentation.model.item.ItemUrlViewModel.Action.GoToDetails
import clean.news.presentation.model.item.ItemUrlViewModel.Action.Share
import clean.news.presentation.model.item.ItemUrlViewModel.State
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import redux.Dispatcher
import redux.Middleware
import redux.Reducer
import redux.Store
import javax.inject.Inject

class ItemUrlViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		val item: Item) : StoreModel<State>() {

	// State

	data class State(val item: Item)

	// Actions

	sealed class Action {
		class GoBack() : Action()
		class GoToDetails() : Action()
		class Share() : Action()
	}

	override fun createStore(): Store<State> {
		// Reducer

		val reducer = object : Reducer<State> {
			override fun reduce(state: State, action: Any): State {
				return state
			}
		}

		// Middleware

		val navigationMiddleware = object : Middleware<State> {
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

		return Store.create(
				reducer,
				State(item),
				Middleware.apply(navigationMiddleware)
		)
	}

}
