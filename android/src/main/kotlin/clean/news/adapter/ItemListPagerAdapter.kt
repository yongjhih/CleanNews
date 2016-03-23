package clean.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import clean.news.R
import clean.news.core.entity.Item
import clean.news.ui.item.list.ItemListScreen

class ItemListPagerAdapter(context: Context) : ViewStatePagerAdapter() {
	private val inflater = LayoutInflater.from(context)

	private val items = arrayListOf(
			ItemListScreen(Item.ListType.TOP),
			ItemListScreen(Item.ListType.NEW),
			ItemListScreen(Item.ListType.SHOW),
			ItemListScreen(Item.ListType.ASK),
			ItemListScreen(Item.ListType.JOBS)
	)

	override fun createView(container: ViewGroup, position: Int): View {
		val view = inflater.inflate(R.layout.item_list_view, container, false)
		container.addView(view)
		return view
	}

	override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
		super.destroyItem(container, position, obj)
		val view = obj as View
		container.removeView(view)
	}

	override fun getPageTitle(position: Int): CharSequence? {
		return items[position].getTitle()
	}

	override fun getCount(): Int {
		return items.size
	}
}
