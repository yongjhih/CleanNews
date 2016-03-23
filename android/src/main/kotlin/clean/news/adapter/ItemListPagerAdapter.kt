package clean.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import clean.news.R
import clean.news.ui.item.list.ItemListScreen

class ItemListPagerAdapter(context: Context) : ViewStatePagerAdapter() {
	private val inflater = LayoutInflater.from(context)

	private val items = arrayListOf(
			ItemListScreen("Top"),
			ItemListScreen("New"),
			ItemListScreen("Show"),
			ItemListScreen("Ask"),
			ItemListScreen("Jobs")
	)

	override fun createView(container: ViewGroup, position: Int): View {
		val view = inflater.inflate(R.layout.item_list_view, container, false)
		container.addView(view)
		return view
	}

	override fun getPageTitle(position: Int): CharSequence? {
		return items[position].type
	}

	override fun getCount(): Int {
		return items.size
	}
}
