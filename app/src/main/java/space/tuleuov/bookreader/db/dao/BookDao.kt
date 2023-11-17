package space.tuleuov.bookreader.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import space.tuleuov.bookreader.db.entity.Book

@Dao
interface BookDao {
    @Insert
    fun insert(book: Book)
    @Update
    fun update(book: Book)
    @Delete
    fun delete(book: Book)
}