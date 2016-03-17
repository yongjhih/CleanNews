package clean.news.usecase.item

import clean.news.repository.item.ItemDiskRepository
import clean.news.repository.item.ItemMemoryRepository
import clean.news.repository.item.ItemNetworkRepository
import javax.inject.Inject

class GetJobStories @Inject constructor(
		disk: ItemDiskRepository,
		memory: ItemMemoryRepository,
		network: ItemNetworkRepository,
		saveItem: SaveItem
) : AbsGetItems(disk.getJobStories(), memory.getJobStories(), network.getJobStories(), saveItem)
