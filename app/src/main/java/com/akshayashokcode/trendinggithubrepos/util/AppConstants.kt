package com.akshayashokcode.trendinggithubrepos.util

import com.akshayashokcode.trendinggithubrepos.R
import java.util.*
import kotlin.collections.HashMap

object AppConstants {
     val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    val LANGUAGE_COLOR_MAP:Map<String, Int> =Collections.unmodifiableMap(
        HashMap<String, Int>().apply {
            put("Java", R.color.colorPrimary)
            put("Kotlin", R.color.color_orange)
            put("Dart", R.color.color_blue)
            put("JavaScript", R.color.color_yellow)
            put("CSS", R.color.color_yellow)
        }
    )
}