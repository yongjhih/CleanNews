package clean.news.ui.main

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.LinearLayout
import butterknife.bindView
import clean.news.R
import clean.news.adapter.ItemListPagerAdapter

class MainView : LinearLayout {
	private val tabLayout: TabLayout by bindView(R.id.tab_layout)
	private val viewPager: ViewPager by bindView(R.id.item_list_view_pager)

	private val viewPagerAdapter: PagerAdapter

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
		viewPagerAdapter = ItemListPagerAdapter(context)
	}

	override fun onFinishInflate() {
		super.onFinishInflate()

		viewPager.adapter = viewPagerAdapter
		tabLayout.setupWithViewPager(viewPager)
	}
}
