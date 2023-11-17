package space.tuleuov.bookreader.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import space.tuleuov.bookreader.db.entity.Book
import space.tuleuov.bookreader.db.entity.Statistics

@Dao
interface StatisticsDao {
    @Insert
    fun insert(stats: Statistics)
    @Update
    fun update(stats: Statistics)
    @Delete
    fun delete(stats: Statistics)
}