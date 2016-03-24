package clean.news.ui.item.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.Toast
import clean.news.flow.ComponentService
import clean.news.presentation.model.item.ItemDetailViewModel
import clean.news.ui.item.detail.ItemDetailScreen.ItemDetailComponent
import flow.Flow
import javax.inject.Inject

class ItemDetailView : RecyclerView {

	@Inject
	lateinit var model: ItemDetailViewModel

	@JvmOverloads
	constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context,
			attrs, defStyle) {
		Flow.getService<ItemDetailComponent>(ComponentService.NAME, context)
				?.inject(this)
	}

	override fun onFinishInflate() {
		super.onFinishInflate()
		Toast.makeText(context, "Detail id: ${model.id}", 300).show()
	}
}
