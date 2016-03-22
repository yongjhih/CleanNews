package clean.news.ui

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import clean.news.R.layout
import clean.news.flow.DaggerServices
import clean.news.flow.SceneDispatcher
import flow.Flow
import flow.KeyDispatcher

abstract class BaseActivity : AppCompatActivity() {
	abstract fun getDefaultKey(): Any

	override fun attachBaseContext(newBase: Context?) {
		val flowContext = Flow.configure(newBase, this)
				.addServicesFactory(DaggerServices())
				.defaultKey(getDefaultKey())
				.dispatcher(KeyDispatcher.configure(this, SceneDispatcher(this)).build())
				.install()

		super.attachBaseContext(flowContext)
	}

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
		setContentView(layout.activity_base)
	}

	override fun onBackPressed() {
		if (!Flow.get(this).goBack()) {
			super.onBackPressed()
		}
	}
}
