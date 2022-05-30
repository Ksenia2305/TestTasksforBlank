package assertions

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun currentDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(formatter)
}

fun dateToTimestamp(pattern: String = "yyyy-MM-dd hh:mm:ss", date: String): Long {
    val formatter = SimpleDateFormat(pattern)
    val date = formatter.parse(date)
    return date.time
}