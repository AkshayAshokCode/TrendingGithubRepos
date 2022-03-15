package com.akshayashokcode.trendinggithubrepos.domain.repository

import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import com.akshayashokcode.trendinggithubrepos.data.util.Resource

interface GitHubRepository {
    suspend fun getTrendingRepos(): Resource<APIResponse>
    suspend fun getSearchedRepos(query: String): Resource<APIResponse>
}