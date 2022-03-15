package com.akshayashokcode.trendinggithubrepos.domain.usecase

import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import com.akshayashokcode.trendinggithubrepos.data.util.Resource
import com.akshayashokcode.trendinggithubrepos.domain.repository.GitHubRepository

class GetSearchedReposUseCase(private val gitHubRepository: GitHubRepository) {

suspend fun execute(query:String):Resource<APIResponse>{
    return gitHubRepository.getSearchedRepos(query)
}
}