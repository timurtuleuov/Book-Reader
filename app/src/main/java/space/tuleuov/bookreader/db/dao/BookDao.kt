package space.tuleuov.bookreader.db.dao

import androidx.room.*
import space.tuleuov.bookreader.db.entity.Book

@Dao
interface BookDao {
    @Insert
    fun insert(book: Book)
    @Update
    fun update(book: Book)
    @Delete
    fun delete(book: Book)

    @Query("SELECT * FROM Book WHERE file_location = :location")
    fun getBookByFileLocation(location: String): Book?
}