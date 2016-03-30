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
import clean.news.ui.item.list.CommentItemView


class CommentAdapter(context: Context) : RecyclerView.Adapter<AbsViewHolder<AbsItem>>() {
	private val inflater = LayoutInflater.from(context)
	private val items = mutableListOf<AbsItem>()

	private val treeMap = mutableMapOf<Long, Node>()

	fun setItems(items: List<Item>) {
		this.items.clear()
		this.items.addAll(items.map {
			when (it.type) {
				Type.COMMENT -> CommentItem(it)
				else -> CommentItem(it)
			}
		})

		treeMap.clear()
		createTreeMap(createTree(this.items), treeMap)

		notifyDataSetChanged()
	}

	override fun onBindViewHolder(viewHolder: AbsViewHolder<AbsItem>, position: Int) {
		viewHolder.bind(position, items[position])
	}

	override fun onCreateViewHolder(parent: ViewGroup, position: Int): AbsViewHolder<AbsItem> {
		val view = inflater.inflate(R.layout.comment_item_view, parent, false) as CommentItemView
		return CommentViewHolder(view) as AbsViewHolder<AbsItem>
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun getItemViewType(position: Int): Int {
		return items[position].getType()
	}

	// Private functions

	// TODO: find an optimized version of this

	private fun createTreeMap(item: Node, map: MutableMap<Long, Node>) {
		map.put(item.data.item.id, item)
		item.children?.map { createTreeMap(it, map) }
	}

	private fun createTree(items: List<AbsItem>): Node {
		return createNode(items.first(), null, items.associateBy { it.item.id })
	}

	private fun createNode(item: AbsItem, parent: Node?, items: Map<Long, AbsItem>): Node {
		return Node(item, parent).apply {
			children = item.item.kids?.map { items[it]?.let { createNode(it, this, items) } }?.filterNotNull()
		}
	}

	// Adapter items

	private class CommentItem(item: Item) : AbsItem(item) {
		override fun getType(): Int {
			return TYPE_STORY_ITEM
		}
	}

	// View holders

	private inner class CommentViewHolder(view: CommentItemView) : AbsViewHolder<CommentItem>(view) {
		override fun bind(position: Int, item: CommentItem) {
			super.bind(position, item)
			val node = treeMap[item.item.id]
			if (node != null) {
				val commentItemView = itemView as CommentItemView
				val parentCollapsed = node.isParentCollapsed()
				val collapsed = node.collapsed

				commentItemView.setVisible(!parentCollapsed)
				commentItemView.setCollapsed(parentCollapsed || collapsed)
				commentItemView.setOnClickListener {
					node.collapsed = false
					notifyDataSetChanged()
				}
				commentItemView.setOnLongClickListener {
					node.collapsed = !collapsed
					notifyDataSetChanged()
					true
				}
			}
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

	// Tree implementation

	private class Node(
			var data: AbsItem,
			var parent: Node?,
			var children: List<Node>? = null,
			var collapsed: Boolean = false) {

		fun isParentCollapsed(): Boolean {
			return parent?.isBranchCollapsed() ?: false
		}

		fun isBranchCollapsed(): Boolean {
			return collapsed || (parent?.isBranchCollapsed() ?: false)
		}
	}

	companion object {
		private const val TYPE_STORY_ITEM = 0
	}
}
