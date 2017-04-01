package clean.news.presentation.model.item

import clean.news.core.entity.Item
import clean.news.presentation.inject.ScreenScope
import clean.news.presentation.model.StoreModel
import clean.news.presentation.model.item.ItemUrlViewModel.Action.GoBack
import clean.news.presentation.model.item.ItemUrlViewModel.Action.GoToDetails
import clean.news.presentation.model.item.ItemUrlViewModel.Action.Share
import clean.news.presentation.model.item.ItemUrlViewModel.State
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationFactory.ItemUrlScreen
import clean.news.presentation.navigation.NavigationService
import redux.api.Dispatcher
import redux.api.Reducer
import redux.api.Store
import redux.api.enhancer.Middleware
import javax.inject.Inject

@ScreenScope(ItemUrlScreen::class)
class ItemUrlViewModel @Inject constructor(
		private val navService: NavigationService,
		private val navFactory: NavigationFactory,
		val item: Item) : StoreModel<State>() {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// State

	data class State(val item: Item)

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Actions

	sealed class Action {
		class GoBack() : Action()
		class GoToDetails() : Action()
		class Share() : Action()
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Reducer

	private fun reducer() = Reducer { state: State, action: Any -> state }

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Middleware

	private fun navigationMiddleware() = Middleware { store: Store<State>, next: Dispatcher, action: Any ->
		val result = next.dispatch(action)
		when (action) {
			is GoBack -> navService.goBack()
			is GoToDetails -> navService.goTo(navFactory.detail(item))
			is Share -> navService.goTo(navFactory.shareDetail(item))
		}
		result
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Middleware

	override fun createStore(): Store<State> {
		return redux.createStore(
				reducer(),
				State(item),
				redux.applyMiddleware(navigationMiddleware())
		)
	}

}
