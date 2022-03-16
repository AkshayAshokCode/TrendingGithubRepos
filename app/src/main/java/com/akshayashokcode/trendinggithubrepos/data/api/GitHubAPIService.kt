package com.akshayashokcode.trendinggithubrepos.data.api

import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubAPIService {
    @GET("search/repositories")
    suspend fun getTrendingRepos(
        @Query("q")
        thisMonth: String,
        @Query("page")
        page:Int,
        @Query("sort")
        sort: String = "stars"
    ): Response<APIResponse>

    @GET("search/repositories")
    suspend fun getSearchedRepos(
        @Query("q")
        query: String,
        @Query("page")
        page:Int,
        @Query("sort")
        sort: String = "stars"
    ): Response<APIResponse>
}