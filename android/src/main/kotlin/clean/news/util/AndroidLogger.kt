package clean.news.util

import android.util.Log
import clean.news.app.util.Logger

class AndroidLogger : Logger {
	override fun v(tag: String, msg: String) {
		Log.v(tag, msg)
	}

	override fun v(tag: String, msg: String, tr: Throwable) {
		Log.v(tag, msg, tr)
	}

	override fun d(tag: String, msg: String) {
		Log.d(tag, msg)
	}

	override fun d(tag: String, msg: String, tr: Throwable) {
		Log.d(tag, msg, tr)
	}

	override fun i(tag: String, msg: String) {
		Log.i(tag, msg)
	}

	override fun i(tag: String, msg: String, tr: Throwable) {
		Log.i(tag, msg, tr)
	}

	override fun w(tag: String, msg: String) {
		Log.w(tag, msg)
	}

	override fun w(tag: String, msg: String, tr: Throwable) {
		Log.w(tag, msg, tr)
	}

	override fun w(tag: String, tr: Throwable) {
		Log.w(tag, tr)
	}

	override fun e(tag: String, msg: String) {
		Log.e(tag, msg)
	}

	override fun e(tag: String, msg: String, tr: Throwable) {
		Log.e(tag, msg, tr)
	}
}
