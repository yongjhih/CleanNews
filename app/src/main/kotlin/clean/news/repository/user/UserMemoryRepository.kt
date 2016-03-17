package clean.news.repository.user

import clean.news.entity.User
import clean.news.repository.Repository

interface UserMemoryRepository : Repository<User, String>
