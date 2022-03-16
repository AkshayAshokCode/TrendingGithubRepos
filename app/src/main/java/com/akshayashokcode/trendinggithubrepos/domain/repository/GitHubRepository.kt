package com.akshayashokcode.trendinggithubrepos.domain.repository

import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import com.akshayashokcode.trendinggithubrepos.data.util.Resource

interface GitHubRepository {
    suspend fun getTrendingRepos(page: Int): Resource<APIResponse>
    suspend fun getSearchedRepos(query: String,page: Int): Resource<APIResponse>
}