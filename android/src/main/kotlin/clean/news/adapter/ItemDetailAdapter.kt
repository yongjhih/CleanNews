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
import clean.news.ui.item.detail.CommentItemView


class ItemDetailAdapter(context: Context) : RecyclerView.Adapter<AbsViewHolder<AbsItem>>() {
	private val inflater = LayoutInflater.from(context)

	private var tree = Node.EMPTY
	private var treeList = mutableListOf<AbsItem>()
	private var treeMap = mapOf<Long, Node>()

	private val loadingItem = LoadingItem()
	private var loading = false

	private val restoredCollapsedIds = mutableListOf<Long>()

	// Public functions

	fun setItems(items: List<Item>) {
		val adapterItems = items.map { CommentItem(it) }
		tree = createTree(adapterItems)

		for ((k, v) in tree.getVisibleCommentMap()) {
			v.collapsed = treeMap[k]?.collapsed ?: restoredCollapsedIds.remove(k)
		}

		buildCaches()
		notifyDataSetChanged()
	}

	fun setLoading(loading: Boolean) {
		if (this.loading != loading) {
			this.loading = loading
		}
	}

	fun getCollapsedIds(): LongArray {
		return tree.toCommentList()
				.filter { it.collapsed }
				.map { it.data.item.id }
				.toLongArray()
	}

	fun setCollapsedIds(ids: LongArray) {
		restoredCollapsedIds.addAll(ids.toList())
	}

	// Overrides

	override fun onBindViewHolder(viewHolder: AbsViewHolder<AbsItem>, position: Int) {
		viewHolder.bind(position, treeList[position])
	}

	override fun onCreateViewHolder(parent: ViewGroup, position: Int): AbsViewHolder<AbsItem> {
		val type = getItemViewType(position)
		@Suppress("UNCHECKED_CAST")
		return when (type) {
			TYPE_COMMENT_ITEM -> {
				CommentViewHolder(inflater.inflate(R.layout.comment_item_view, parent, false) as CommentItemView)
			}
			TYPE_LOADING_ITEM -> {
				LoadingViewHolder(inflater.inflate(R.layout.loading_view, parent, false))
			}
			else -> throw IllegalStateException("Cannot create view of type $type.")
		} as AbsViewHolder<AbsItem>
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
		treeList = tree.getVisibleCommentList().map { it.data }.toMutableList()
		treeMap = tree.getVisibleCommentMap()
	}

	private fun createTree(items: List<ItemItem>): Node {
		return createNode(items.first(), null, items.associateBy { it.item.id })
	}

	private fun createNode(item: ItemItem, parent: Node?, items: Map<Long, ItemItem>): Node {
		return Node(item, parent).apply {
			children = item.item.kids?.map { items[it]?.let { createNode(it, this, items) } }?.filterNotNull()
		}
	}

	// Adapter items

	private class CommentItem(item: Item) : ItemItem(item) {
		override fun getType() = TYPE_COMMENT_ITEM
	}

	private class LoadingItem() : AbsItem() {
		override fun getType() = TYPE_LOADING_ITEM
	}

	// View holders

	private inner class CommentViewHolder(view: CommentItemView) : ItemViewHolder<CommentItem>(view) {
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

	private class LoadingViewHolder(view: View) : AbsViewHolder<LoadingItem>(view)

	// Abstract inner classes

	abstract class ItemItem(var item: Item) : AbsItem() {
		companion object {
			val EMPTY = object : ItemItem(Item.EMPTY) {
				override fun getType() = -1
			}
		}
	}

	abstract class AbsItem() {
		abstract fun getType(): Int
	}

	abstract class ItemViewHolder<T : ItemItem>(view: View) : AbsViewHolder<T>(view) {
		override fun bind(position: Int, item: T) {
			if (itemView is Bindable<*> && itemView.bindType() == Item::class.java) {
				@Suppress("UNCHECKED_CAST")
				(itemView as Bindable<Item>).bind(item.item)
			}
		}
	}

	abstract class AbsViewHolder<T : AbsItem>(view: View) : RecyclerView.ViewHolder(view) {
		open fun bind(position: Int, item: T) {
		}
	}

	// Tree implementation
	// TODO: I'm sure a lot of this could be faster, but it's fast enough for me to be lazy right now.

	private class Node(
			var data: ItemItem,
			var parent: Node?,
			var children: List<Node>? = null,
			var collapsed: Boolean = false) {

		fun toCommentList(items: MutableList<Node> = arrayListOf()): MutableList<Node> {
			return items.apply {
				if (this@Node.data.item.type == Item.Type.COMMENT) {
					add(this@Node)
				}
				children?.map { it.toCommentList(this) }
			}
		}

		fun getVisibleCommentList(items: MutableList<Node> = arrayListOf()): MutableList<Node> {
			return items.apply {
				if (this@Node.data.item.type == Item.Type.COMMENT) {
					add(this@Node)
				}
				if (!collapsed) {
					children?.map { it.getVisibleCommentList(this) }
				}
			}
		}

		fun getVisibleCommentMap(map: MutableMap<Long, Node> = mutableMapOf()): MutableMap<Long, Node> {
			return map.apply {
				if (this@Node.data.item.type == Item.Type.COMMENT) {
					put(data.item.id, this@Node)
				}
				if (!collapsed) {
					children?.map { it.getVisibleCommentMap(this) }
				}
			}
		}

		companion object {
			val EMPTY = Node(ItemItem.EMPTY, null)
		}
	}

	companion object {
		private const val TYPE_COMMENT_ITEM = 0
		private const val TYPE_LOADING_ITEM = 1
	}
}
