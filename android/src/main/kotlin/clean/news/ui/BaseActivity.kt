package clean.news.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import clean.news.CleanNewsApplication
import clean.news.R
import clean.news.flow.keychanger.CompositeKeyChanger
import clean.news.flow.keychanger.SceneKeyChanger
import clean.news.flow.keychanger.SceneKeyChanger.WithLayout
import clean.news.flow.parceler.PaperKeyParceler
import clean.news.flow.service.DaggerService
import clean.news.flow.service.MortarService
import clean.news.navigation.FlowNavigationService
import flow.Flow
import flow.KeyDispatcher

abstract class BaseActivity : AppCompatActivity() {

	abstract fun getDefaultKey(): Any

	override fun attachBaseContext(newBase: Context) {
		val applicationComponent = CleanNewsApplication.get(newBase).component()
		val scope = CleanNewsApplication.get(newBase).scope()

		val keyChanger = CompositeKeyChanger()
		keyChanger.addDispatcher(WithLayout::class, SceneKeyChanger(this))

		val context = Flow.configure(newBase, this)
				.keyParceler(PaperKeyParceler())
				.dispatcher(KeyDispatcher.configure(this, keyChanger).build())
				.defaultKey(getDefaultKey())
				.addServicesFactory(DaggerService(applicationComponent))
				.addServicesFactory(MortarService(scope))
				.install()

		super.attachBaseContext(context)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_base)
	}

	override fun onResume() {
		super.onResume()
		val applicationComponent = CleanNewsApplication.get(this).component()
		val navigationService = applicationComponent.navigationService() as FlowNavigationService
		navigationService.setContext(this)
	}

	override fun onPause() {
		val applicationComponent = CleanNewsApplication.get(this).component()
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
