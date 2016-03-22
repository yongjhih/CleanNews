package clean.news

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import clean.news.flow.DaggerServices
import clean.news.ui.main.MainScreen
import flow.Direction
import flow.Flow
import flow.KeyChanger
import flow.KeyDispatcher
import flow.State
import flow.TraversalCallback

class MainActivity : AppCompatActivity() {
	override fun attachBaseContext(newBase: Context?) {
		val flowContext = Flow.configure(newBase, this)
				.addServicesFactory(DaggerServices())
				.defaultKey(MainScreen())
				.dispatcher(KeyDispatcher.configure(this, Changer()).build())
				.install()

		super.attachBaseContext(flowContext)
	}

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
	}

	private class Changer : KeyChanger() {
		override fun changeKey(outgoingState: State?, incomingState: State?, direction: Direction?,
				incomingContexts: MutableMap<Any, Context>?, callback: TraversalCallback?) {
			throw UnsupportedOperationException()
		}

	}
}
