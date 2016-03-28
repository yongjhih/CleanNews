package clean.news.ui.item.url

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import clean.news.presentation.model.item.ItemUrlViewModel
import javax.inject.Inject

class ItemUrlView : RelativeLayout {
	@Inject
	lateinit var model: ItemUrlViewModel

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
	}
}
