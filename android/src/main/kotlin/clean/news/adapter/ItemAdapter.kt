package clean.news.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import clean.news.R
import clean.news.adapter.ItemAdapter.AbsItem
import clean.news.adapter.ItemAdapter.AbsViewHolder
import clean.news.core.entity.Item
import clean.news.core.entity.Item.Type

class ItemAdapter(context: Context) : RecyclerView.Adapter<AbsViewHolder<AbsItem>>() {
	private val inflater = LayoutInflater.from(context)
	private val items = mutableListOf<AbsItem>()

	var itemClickListener: ((Long) -> Any?)? = null

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

	override fun onCreateViewHolder(parent: ViewGroup, position: Int): AbsViewHolder<AbsItem> {
		val view = inflater.inflate(R.layout.item_view, parent, false)
		return StoryViewHolder(view)
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

	private inner class StoryViewHolder(view: View) : AbsViewHolder<AbsItem>(view) {
		override fun bind(position: Int, item: AbsItem) {
			super.bind(position, item)
			itemView.setOnClickListener { itemClickListener?.invoke(item.item.id) }
		}
	}

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
