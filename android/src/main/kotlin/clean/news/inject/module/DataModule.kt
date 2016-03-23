package clean.news.inject.module

import clean.news.app.repository.item.ItemDiskRepository
import clean.news.app.repository.item.ItemMemoryRepository
import clean.news.app.repository.item.ItemNetworkRepository
import clean.news.app.repository.user.UserDiskRepository
import clean.news.app.repository.user.UserMemoryRepository
import clean.news.app.repository.user.UserNetworkRepository
import clean.news.data.lru.ItemLruRepository
import clean.news.data.lru.UserLruRepository
import clean.news.data.retrofit.ItemRetrofitRepository
import clean.news.data.retrofit.UserRetrofitRepository
import clean.news.data.retrofit.moshi.adapter.ItemTypeAdapter
import clean.news.data.retrofit.moshi.adapter.UnixTimeAdapter
import clean.news.data.retrofit.service.ItemService
import clean.news.data.retrofit.service.UserService
import clean.news.data.sqlite.ItemSqliteRepository
import clean.news.data.sqlite.UserSqliteRepository
import clean.news.presentation.inject.ApplicationScope
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class DataModule {
	@Provides
	@ApplicationScope
	fun moshi(): Moshi {
		return Moshi.Builder()
				.add(UnixTimeAdapter())
				.add(ItemTypeAdapter())
				.build()
	}

	@Provides
	@ApplicationScope
	fun okHttpClient(): OkHttpClient {
		val logging = HttpLoggingInterceptor()
		logging.level = Level.BASIC

		return OkHttpClient.Builder()
				.addInterceptor(logging)
				.build()
	}

	@Provides
	@ApplicationScope
	fun retrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
		return Retrofit.Builder()
				.client(client)
				.baseUrl("https://hacker-news.firebaseio.com/v0/")
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
				.build()
	}

	@Provides
	@ApplicationScope
	fun itemService(retrofit: Retrofit): ItemService {
		return retrofit.create(ItemService::class.java)
	}

	@Provides
	@ApplicationScope
	fun userService(retrofit: Retrofit): UserService {
		return retrofit.create(UserService::class.java)
	}

	// Item

	@Provides
	@ApplicationScope
	fun itemDiskRepository(): ItemDiskRepository {
		return ItemSqliteRepository()
	}

	@Provides
	@ApplicationScope
	fun itemMemoryRepository(): ItemMemoryRepository {
		return ItemLruRepository()
	}

	@Provides
	@ApplicationScope
	fun itemNetworkRepository(itemService: ItemService): ItemNetworkRepository {
		return ItemRetrofitRepository(itemService)
	}

	// User

	@Provides
	@ApplicationScope
	fun userDiskRepository(): UserDiskRepository {
		return UserSqliteRepository()
	}

	@Provides
	@ApplicationScope
	fun userMemoryRepository(): UserMemoryRepository {
		return UserLruRepository()
	}

	@Provides
	@ApplicationScope
	fun userNetworkRepository(userService: UserService): UserNetworkRepository {
		return UserRetrofitRepository(userService)
	}
}
