package space.tuleuov.bookreader.db.entity

import androidx.room.*

@Entity(
    tableName = "book",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["uid"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["file_location"], unique = true)]
)
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

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
    val cover: String?,

    @ColumnInfo(name = "genre")
    val genre: String?
)
