package ptit.iot.smarthome.utils.helper

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

val TAG = "DATE TIME"
@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTime(): String {
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    return currentDateTime.format(formatter).toString()
}

fun getTimeStamp(): Long {
    return System.currentTimeMillis()
}

fun dateToTimestamp(date: Date): Long {
    Log.i(TAG, "dateToTimestamp: $date")
    return date.time
}

@SuppressLint("NewApi")
fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return currentDate.format(formatter).toString()
}

@SuppressLint("NewApi", "SimpleDateFormat")
fun Long.convertToDateTime() : String {
    Log.i(TAG, "convert timestamp to datetime: $this")
    val date =  Date(this);
    val sdf =  SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    return sdf.format(date);
}

@SuppressLint("SimpleDateFormat")
fun Long.convertToStartOfDay(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

@SuppressLint("SimpleDateFormat")
fun Long.convertToEndOfDay(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    return calendar.timeInMillis
}