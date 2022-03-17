package com.akshayashokcode.trendinggithubrepos.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.akshayashokcode.trendinggithubrepos.R
import com.akshayashokcode.trendinggithubrepos.util.AppConstants.DATE_TIME_FORMAT
import com.akshayashokcode.trendinggithubrepos.util.AppConstants.LANGUAGE_COLOR_MAP
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

    fun getColorByLanguage(
        context: Context,
        language: String?,
    ): Int? {
        return if (LANGUAGE_COLOR_MAP.containsKey(language)) LANGUAGE_COLOR_MAP[language]?.let {
            ContextCompat.getColor(context,
                it)
        } else ContextCompat.getColor(context,
            R.color.colorPrimary)
    }


}