package clean.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import clean.news.R
import clean.news.core.entity.Item.ListType
import clean.news.inject.ModuleContext
import clean.news.ui.item.list.ItemListKey.ItemListModule
import clean.news.ui.item.list.ItemListView

class ItemListPagerAdapter(context: Context) : ViewStatePagerAdapter() {
	val inflater = LayoutInflater.from(context)

	private val items = arrayListOf(
			ItemListModule(ListType.TOP),
			ItemListModule(ListType.NEW),
			ItemListModule(ListType.SHOW),
			ItemListModule(ListType.ASK),
			ItemListModule(ListType.JOB)
	)

	override fun createView(container: ViewGroup, position: Int): View {
		val inflater =  inflater.cloneInContext(ModuleContext(inflater.context, items[position]))
		val view = inflater.inflate(R.layout.item_list_view, container, false) as ItemListView
		container.addView(view)
		return view
	}

	override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
		super.destroyItem(container, position, obj)
		val view = obj as View
		container.removeView(view)
	}

	override fun getPageTitle(position: Int): CharSequence? {
		return items[position].listType().name
	}

	override fun getCount(): Int {
		return items.size
	}
}
