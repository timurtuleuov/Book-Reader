package space.tuleuov.bookreader

import android.app.Application
import androidx.room.Room
import space.tuleuov.bookreader.db.Database


class BookReaderApp: Application() {

    lateinit var database: Database

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "bookReader"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

}