package space.tuleuov.bookreader.db.entity

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return if (value == null) null else LocalDateTime.parse(value, formatter)
    }

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? {
        return if (date == null) null else date.format(formatter)
    }
}