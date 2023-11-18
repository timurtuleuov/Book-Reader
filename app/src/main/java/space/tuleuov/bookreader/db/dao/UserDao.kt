package space.tuleuov.bookreader.db.dao

import androidx.room.*
import space.tuleuov.bookreader.db.entity.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)
    @Update
    fun update(user: User)
    @Delete
    fun delete(user: User)
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): User?
}