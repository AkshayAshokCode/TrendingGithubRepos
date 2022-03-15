package com.akshayashokcode.trendinggithubrepos.data.model


import com.google.gson.annotations.SerializedName
// id, full_name, owner, description, created_at, stargazers_count, forks_count, language, topics
data class Item(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("topics")
    val topics: List<String>,
)