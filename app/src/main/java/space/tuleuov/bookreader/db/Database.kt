package space.tuleuov.bookreader.db

import androidx.room.RoomDatabase
import space.tuleuov.bookreader.db.dao.BookDao
import space.tuleuov.bookreader.db.dao.StatisticsDao
import space.tuleuov.bookreader.db.dao.UserDao
import space.tuleuov.bookreader.db.entity.User
import androidx.room.Database
import androidx.room.TypeConverters
import space.tuleuov.bookreader.db.entity.Book
import space.tuleuov.bookreader.db.entity.Converter
import space.tuleuov.bookreader.db.entity.Statistics

@Database(
    entities = [
        User::class,
        Book::class,
        Statistics::class
    ],
    version = 1
)
@TypeConverters(Converter::class)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    abstract fun statsDao(): StatisticsDao


}
