package clean.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import clean.news.adapter.ItemAdapter.AbsItem
import clean.news.adapter.ItemAdapter.AbsViewHolder
import clean.news.core.entity.Item
import clean.news.core.entity.Item.Type

class ItemAdapter : RecyclerView.Adapter<AbsViewHolder<AbsItem>>() {
	private val items = mutableListOf<AbsItem>()

	fun setItems(items: List<Item>) {
		this.items.clear()
		this.items.addAll(items.map {
			when (it.type) {
				Type.STORY -> StoryItem(it)
				else -> StoryItem(it)
			}
		})
		notifyDataSetChanged()
	}

	override fun onBindViewHolder(viewHolder: AbsViewHolder<AbsItem>, position: Int) {
		viewHolder.bind(position, items[position])
	}

	override fun onCreateViewHolder(viewHolder: ViewGroup, position: Int): AbsViewHolder<AbsItem> {
		// TODO: inflate view holder
		throw UnsupportedOperationException()
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun getItemViewType(position: Int): Int {
		return items[position].getType()
	}

	// Adapter items

	private class StoryItem(item: Item) : AbsItem(item) {
		override fun getType(): Int {
			return TYPE_STORY_ITEM
		}
	}

	// View holders

	private class StoryViewHolder(view: View) : AbsViewHolder<AbsItem>(view) {

	}

	// Abstract inner classes

	abstract class AbsItem(val item: Item) {
		abstract fun getType(): Int
	}

	abstract class AbsViewHolder<T : AbsItem>(view: View) : RecyclerView.ViewHolder(view) {
		open fun bind(position: Int, item: T) {
		}
	}

	companion object {
		private const val TYPE_STORY_ITEM = 0
	}
}
