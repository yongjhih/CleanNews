package clean.news.app.repository.user

import clean.news.app.repository.Repository
import clean.news.core.entity.User

interface UserNetworkRepository : Repository<User, String>
