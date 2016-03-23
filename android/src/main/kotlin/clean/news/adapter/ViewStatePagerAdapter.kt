package clean.news.adapter

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

abstract class ViewStatePagerAdapter : PagerAdapter() {
	private val KEY_VIEW_STATE_SIZE = "viewStateSize"

	private val savedStates = SparseArray<SparseArray<Parcelable>?>()
	private val views = SparseArray<View?>()

	abstract fun createView(container: ViewGroup, position: Int): View

	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		var view = views.get(position)
		if (view != null) {
			return view
		}

		view = createView(container, position)
		if (view.isSaveFromParentEnabled) {
			val savedState = savedStates.get(position)
			if (savedState != null) {
				view.restoreHierarchyState(savedState)
				savedStates.remove(position)
			}
		}

		views.put(position, view)

		return view
	}

	override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
		val view = obj as View
		if (view.isSaveFromParentEnabled) {
			val savedState = SparseArray<Parcelable>()
			view.saveHierarchyState(savedState)
			savedStates.put(position, savedState)
		}
		views.remove(position)
	}

	override fun isViewFromObject(view: View?, obj: Any?): Boolean {
		return view == obj
	}

	override fun saveState(): Parcelable? {
		val state = Bundle()
		var size = 0
		for (i in 0..savedStates.size() - 1) {
			val savedState = savedStates.get(i)
			if (savedState != null) {
				state.putSparseParcelableArray("v$i", savedState)
				size++
			}
		}
		for (i in 0..views.size() - 1) {
			val view = views.get(i)
			if (view != null && view.isSaveFromParentEnabled) {
				val savedState = SparseArray<Parcelable>()
				view.saveHierarchyState(savedState)
				state.putSparseParcelableArray("v$i", savedState)
				size++
			}
		}
		state.putInt(KEY_VIEW_STATE_SIZE, size)
		return state
	}

	override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
		if (state is Bundle) {
			state.classLoader = loader
			views.clear()
			val size = state.getInt(KEY_VIEW_STATE_SIZE)
			for (i in 0..size - 1) {
				val savedState = state.getSparseParcelableArray<Parcelable>("v$i")
				savedStates.put(i, savedState)
			}
		}
	}
}
