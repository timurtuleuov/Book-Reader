package space.tuleuov.bookreader.db.repositiry

import space.tuleuov.bookreader.db.dao.UserDao
import space.tuleuov.bookreader.db.entity.User

class UserRepository(private val userDao: UserDao) {

    // Другие методы репозитория

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}
