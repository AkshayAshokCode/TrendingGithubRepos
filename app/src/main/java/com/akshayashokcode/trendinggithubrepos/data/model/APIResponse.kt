package com.akshayashokcode.trendinggithubrepos.data.model


import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)