package clean.news.app.data.user

import clean.news.app.data.DataSource
import clean.news.core.entity.User

interface UserDiskDataSource : DataSource<User, String>
