package clean.news.usecase.item

import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.repository.item.ItemNetworkRepository
import javax.inject.Inject

class GetTopStories @Inject constructor(
		disk: ItemDiskRepository,
		memory: ItemMemoryRepository,
		network: ItemNetworkRepository,
		saveItem: SaveItem
) : AbsGetItems(disk.getTopStories(), memory.getTopStories(), network.getTopStories(), saveItem)
