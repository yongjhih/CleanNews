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
		// Create indexes
		val oldIds = this.items.map { it.item.id }
		val newIds = items.map { it.id }
		val newItemMap = items.associateBy { it.id }

		// Delete old entries
		this.items.removeAll { it.item.id !in newIds }
		// Update existing entries
		this.items.filter { it.item.id in newIds }.forEach { it.item = newItemMap[it.item.id]!! }
		// Add new entries
		this.items.addAll(items
				.filter { it.id !in oldIds }
				.map {
					when (it.type) {
						Type.COMMENT -> CommentItem(it)
						else -> CommentItem(it)
					}
				})

		// Create tree and tree map
		val newTreeMap = createTreeMap(createTree(items))

		// Update collapsed values with old values
		for ((id, item) in newTreeMap) {
			val oldNode = treeMap[id]
			if (oldNode != null) {
				item.collapsed = oldNode.collapsed
			}
		}

		// Update tree map
		treeMap.clear()
		treeMap.putAll(newTreeMap)

		// Update views
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

	private fun createTreeMap(item: Node, map: MutableMap<Long, Node> = mutableMapOf()): MutableMap<Long, Node> {
		return map.apply {
			put(item.data.id, item)
			item.children?.map { createTreeMap(it, this) }
		}
	}

	private fun createTree(items: List<Item>): Node {
		return createNode(items.first(), null, items.associateBy { it.id })
	}

	private fun createNode(item: Item, parent: Node?, items: Map<Long, Item>): Node {
		return Node(item, parent).apply {
			children = item.kids?.map { items[it]?.let { createNode(it, this, items) } }?.filterNotNull()
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

				commentItemView.setVisible(!parentCollapsed)
				commentItemView.setCollapsed(parentCollapsed || node.collapsed)
				commentItemView.setCollapseClickListener {
					node.collapsed = !node.collapsed
					notifyDataSetChanged()
				}
			}
		}
	}

	// Abstract inner classes

	abstract class AbsItem(var item: Item) {
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

	class Node(
			var data: Item,
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
