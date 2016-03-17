package clean.news.usecase

class Strategy(private val flag: Int) {
	val useDisk: Boolean by lazy { flag and DISK == 0 }
	val useMemory: Boolean by lazy { flag and MEMORY == 0 }
	val useNetwork: Boolean by lazy { flag and NETWORK == 0 }

	companion object {
		const val DISK = 0
		const val MEMORY = 1
		const val NETWORK = 2

		const val WARM = DISK or MEMORY or NETWORK
	}
}
