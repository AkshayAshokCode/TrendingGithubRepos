package com.akshayashokcode.trendinggithubrepos.data.model


import com.google.gson.annotations.SerializedName

// id, Login, avatar_url
data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
)