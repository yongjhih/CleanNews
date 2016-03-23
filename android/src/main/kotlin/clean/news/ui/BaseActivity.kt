package clean.news.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import clean.news.CleanNewsApplication
import clean.news.R
import clean.news.flow.ComponentService
import clean.news.flow.SceneDispatcher
import clean.news.inject.component.ApplicationComponent
import clean.news.navigation.FlowNavigationService
import flow.Flow
import flow.KeyDispatcher

abstract class BaseActivity : AppCompatActivity() {
	abstract fun getDefaultKey(): Any

	private lateinit var applicationComponent: ApplicationComponent

	override fun attachBaseContext(newBase: Context) {
		applicationComponent = CleanNewsApplication.get(newBase).component()
		val context = Flow.configure(newBase, this)
				.addServicesFactory(ComponentService(applicationComponent))
				.dispatcher(KeyDispatcher.configure(this, SceneDispatcher(this)).build())
				.defaultKey(getDefaultKey())
				.install()

		super.attachBaseContext(context)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_base)
	}

	override fun onResume() {
		super.onResume()
		val navigationService = applicationComponent.navigationService() as FlowNavigationService
		navigationService.setContext(this)
	}

	override fun onPause() {
		val navigationService = applicationComponent.navigationService() as FlowNavigationService
		navigationService.removeContext()
		super.onPause()
	}

	override fun onBackPressed() {
		if (!Flow.get(this).goBack()) {
			super.onBackPressed()
		}
	}
}
