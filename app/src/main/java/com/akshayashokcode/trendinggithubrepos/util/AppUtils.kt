package com.akshayashokcode.trendinggithubrepos.util

import com.akshayashokcode.trendinggithubrepos.util.AppConstants.DATE_TIME_FORMAT
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AppUtils {

    fun getDate(dateString: String?): String? {
        return try {
            val format1 = SimpleDateFormat(DATE_TIME_FORMAT)
            val date: Date = format1.parse(dateString)
            val sdf: DateFormat = SimpleDateFormat("MMM d, yyyy")
            sdf.format(date)
        } catch (ex: Exception) {
            ex.printStackTrace()
            "xx"
        }
    }


}