package clean.news.usecase

class Strategy(private val flag: Int) {
	val disk: Boolean by lazy { flag and DISK == 0 }
	val memory: Boolean by lazy { flag and MEMORY == 0 }
	val network: Boolean by lazy { flag and NETWORK == 0 }
	val first: Boolean by lazy { flag and FIRST == 0 }

	companion object {
		const val DISK = 0
		const val MEMORY = 1
		const val NETWORK = 2
		const val FIRST = 4

		const val WARM = DISK or MEMORY or NETWORK
	}
}
