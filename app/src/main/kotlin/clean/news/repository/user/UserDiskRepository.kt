package clean.news.repository.user

import clean.news.entity.User
import clean.news.repository.Repository

interface UserDiskRepository : Repository<User, String>
