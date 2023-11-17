package space.tuleuov.bookreader.db.entity

import androidx.room.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    tableName = "statistics",
    foreignKeys = [ForeignKey(
        entity = Book::class,
        parentColumns = ["id"],
        childColumns = ["book_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("book_id")]
)
data class Statistics(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "book_id") val bookId: Int,

    @ColumnInfo(name = "date") val date:LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "books_read") val booksRead: Int,
    @ColumnInfo(name = "page_read") val pageRead: Int,
    @ColumnInfo(name = "reading_seconds") val readingSeconds: Long
)
