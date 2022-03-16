package com.akshayashokcode.trendinggithubrepos.data.repository.dataSource

import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import retrofit2.Response

interface GitHubRemoteDataSource {
    suspend fun getTrendingRepos(page:Int): Response<APIResponse>
    suspend fun getSearchedRepos(query: String,page:Int): Response<APIResponse>
}