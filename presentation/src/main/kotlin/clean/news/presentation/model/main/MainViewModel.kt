package clean.news.presentation.model.main

import clean.news.presentation.inject.ClassScope
import clean.news.presentation.navigation.NavigationFactory
import clean.news.presentation.navigation.NavigationService
import javax.inject.Inject

@ClassScope(MainViewModel::class)
class MainViewModel @Inject constructor(
		private val navigationService: NavigationService,
		private val navigationFactory: NavigationFactory) {
}
