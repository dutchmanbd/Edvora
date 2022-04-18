package com.careers.utilities

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    //    02/24/2022 07:00 PM
    private const val SERVER_DATE_TIME_FORMAT = "MM/dd/yyyy hh:mm a"

    private val serverDateFormatter = SimpleDateFormat(
        SERVER_DATE_TIME_FORMAT, Locale.ENGLISH
    )

    fun getTimeStamp(serverDate: String): Long {
        if (serverDate.isEmpty())
            return 0L
        return try {
            val parseDate: Date? = serverDateFormatter.parse(serverDate)
            parseDate?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

}