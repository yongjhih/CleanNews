package clean.news.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import clean.news.R
import clean.news.adapter.ItemDetailAdapter.AbsItem
import clean.news.adapter.ItemDetailAdapter.AbsViewHolder
import clean.news.core.entity.Item
import clean.news.ui.item.list.CommentItemView


class ItemDetailAdapter(context: Context) : RecyclerView.Adapter<AbsViewHolder<AbsItem>>() {
	private val inflater = LayoutInflater.from(context)

	private var tree = Node.EMPTY
	private var treeList = listOf<AbsItem>()
	private var treeMap = mapOf<Long, Node>()

	fun setItems(items: List<Item>) {
		tree = createTree(items.map { createItem(it) })
		buildCaches()
		notifyDataSetChanged()
	}

	override fun onBindViewHolder(viewHolder: AbsViewHolder<AbsItem>, position: Int) {
		viewHolder.bind(position, treeList[position])
	}

	override fun onCreateViewHolder(parent: ViewGroup, position: Int): AbsViewHolder<AbsItem> {
		val view = inflater.inflate(R.layout.comment_item_view, parent, false) as CommentItemView
		return CommentViewHolder(view) as AbsViewHolder<AbsItem>
	}

	override fun getItemCount(): Int {
		return treeList.size
	}

	override fun getItemViewType(position: Int): Int {
		return treeList[position].getType()
	}

	// Private functions

	private fun toggleCollapsed(node: Node) {
		node.collapsed = !node.collapsed
		buildCaches()
		notifyDataSetChanged()
	}

	private fun buildCaches() {
		treeList = tree.getList().map { it.data }
		treeMap = tree.getMap()
	}

	private fun createItem(item: Item): AbsItem {
		return when (item.type) {
			Item.Type.STORY -> CommentItem(item)
			else -> CommentItem(item)
		}
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
				val collapsed = node.collapsed
				commentItemView.setCollapsed(collapsed)
				commentItemView.setCollapseClickListener { toggleCollapsed(node) }
			}
		}
	}

	// Abstract inner classes

	abstract class AbsItem(var item: Item) {
		abstract fun getType(): Int

		companion object {
			val EMPTY = object : AbsItem(Item.EMPTY) {
				override fun getType() = -1
			}
		}
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

		fun getList(items: MutableList<Node> = arrayListOf()): MutableList<Node> {
			return items.apply {
				add(this@Node)
				if (!collapsed) {
					children?.map { it.getList(this) }
				}
			}
		}

		fun getMap(map: MutableMap<Long, Node> = mutableMapOf()): MutableMap<Long, Node> {
			return map.apply {
				put(data.item.id, this@Node)
				if (!collapsed) {
					children?.map { it.getMap(this) }
				}
			}
		}

		companion object {
			val EMPTY = Node(AbsItem.EMPTY, null)
		}
	}

	companion object {
		private const val TYPE_STORY_ITEM = 0
	}
}
