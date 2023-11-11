package space.tuleuov.bookreader.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid") val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name ="email") val email: String,
    @ColumnInfo(name ="password") val password: String,
    @ColumnInfo(name ="avatar") val avatar: String?,
    @ColumnInfo(name ="status") val status: String?,
    @ColumnInfo(name ="level") val level: Int
)