package com.akshayashokcode.trendinggithubrepos.domain.usecase

import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import com.akshayashokcode.trendinggithubrepos.data.util.Resource
import com.akshayashokcode.trendinggithubrepos.domain.repository.GitHubRepository

class GetTrendingReposUseCase(private val gitHubRepository: GitHubRepository) {
    suspend fun execute(page: Int): Resource<APIResponse> {
        return gitHubRepository.getTrendingRepos(page)
    }
}