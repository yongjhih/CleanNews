package clean.news.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import clean.news.R
import clean.news.adapter.CommentAdapter.AbsItem
import clean.news.adapter.CommentAdapter.AbsViewHolder
import clean.news.core.entity.Item
import clean.news.core.entity.Item.Type


class CommentAdapter(context: Context) : RecyclerView.Adapter<AbsViewHolder<AbsItem>>() {
	private val inflater = LayoutInflater.from(context)
	private val items = mutableListOf<AbsItem>()

	fun setItems(items: List<Item>) {
		this.items.clear()
		this.items.addAll(items.map {
			when (it.type) {
				Type.COMMENT -> CommentItem(it)
				else -> CommentItem(it)
			}
		})
		notifyDataSetChanged()
	}

	override fun onBindViewHolder(viewHolder: AbsViewHolder<AbsItem>, position: Int) {
		viewHolder.bind(position, items[position])
	}

	override fun onCreateViewHolder(parent: ViewGroup, position: Int): AbsViewHolder<AbsItem> {
		val view = inflater.inflate(R.layout.comment_item_view, parent, false)
		return CommentViewHolder(view)
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun getItemViewType(position: Int): Int {
		return items[position].getType()
	}

	// Adapter items

	private class CommentItem(item: Item) : AbsItem(item) {
		override fun getType(): Int {
			return TYPE_STORY_ITEM
		}
	}

	// View holders

	private inner class CommentViewHolder(view: View) : AbsViewHolder<AbsItem>(view)

	// Abstract inner classes

	abstract class AbsItem(val item: Item) {
		abstract fun getType(): Int
	}

	abstract class AbsViewHolder<T : AbsItem>(view: View) : RecyclerView.ViewHolder(view) {
		open fun bind(position: Int, item: T) {
			if (itemView is Bindable<*> && itemView.bindType() == Item::class.java) {
				@Suppress("UNCHECKED_CAST")
				(itemView as Bindable<Item>).bind(item.item)
			}
		}
	}

	companion object {
		private const val TYPE_STORY_ITEM = 0
	}
}
