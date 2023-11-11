package space.tuleuov.bookreader.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "book",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["uid"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "file_location")
    val fileLocation: String,

    @ColumnInfo(name = "book_name")
    val bookName: String,

    @ColumnInfo(name = "annotation")
    val annotation: String?,

    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "series")
    val series: String?,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "bookmark")
    val bookmark: String?,

    @ColumnInfo(name = "last_opened_time")
    val lastOpenedTime: Long,

    @ColumnInfo(name = "cover")
    val cover: String,

    @ColumnInfo(name = "genre")
    val genre: String?
)
