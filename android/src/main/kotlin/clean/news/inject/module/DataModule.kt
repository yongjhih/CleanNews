package clean.news.inject.module

import clean.news.app.data.item.ItemDiskDataSource
import clean.news.app.data.item.ItemMemoryDataSource
import clean.news.app.data.item.ItemNetworkDataSource
import clean.news.app.data.user.UserDiskDataSource
import clean.news.app.data.user.UserMemoryDataSource
import clean.news.app.data.user.UserNetworkDataSource
import clean.news.data.lru.ItemLruDataSource
import clean.news.data.lru.UserLruDataSource
import clean.news.data.retrofit.ItemRetrofitDataSource
import clean.news.data.retrofit.UserRetrofitDataSource
import clean.news.data.retrofit.moshi.adapter.ItemTypeAdapter
import clean.news.data.retrofit.moshi.adapter.UnixTimeAdapter
import clean.news.data.retrofit.service.ItemService
import clean.news.data.retrofit.service.UserService
import clean.news.data.sqlite.ItemSqliteDataSource
import clean.news.data.sqlite.UserSqliteDataSource
import clean.news.presentation.inject.ApplicationScope
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
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
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
	fun itemDiskDataSource(): ItemDiskDataSource {
		return ItemSqliteDataSource()
	}

	@Provides
	@ApplicationScope
	fun itemMemoryDataSource(): ItemMemoryDataSource {
		return ItemLruDataSource()
	}

	@Provides
	@ApplicationScope
	fun itemNetworkDataSource(itemService: ItemService): ItemNetworkDataSource {
		return ItemRetrofitDataSource(itemService)
	}

	// User

	@Provides
	@ApplicationScope
	fun userDiskDataSource(): UserDiskDataSource {
		return UserSqliteDataSource()
	}

	@Provides
	@ApplicationScope
	fun userMemoryDataSource(): UserMemoryDataSource {
		return UserLruDataSource()
	}

	@Provides
	@ApplicationScope
	fun userNetworkDataSource(userService: UserService): UserNetworkDataSource {
		return UserRetrofitDataSource(userService)
	}
}
