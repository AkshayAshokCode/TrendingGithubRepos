package com.akshayashokcode.trendinggithubrepos.data.repository

import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import com.akshayashokcode.trendinggithubrepos.data.repository.dataSource.GitHubRemoteDataSource
import com.akshayashokcode.trendinggithubrepos.data.util.Resource
import com.akshayashokcode.trendinggithubrepos.domain.repository.GitHubRepository
import retrofit2.Response

class GitHubRepositoryImpl(
    private val gitHubRemoteDataSource: GitHubRemoteDataSource
):GitHubRepository {
    override suspend fun getTrendingRepos(): Resource<APIResponse> {
        return responseToResource(
            gitHubRemoteDataSource.getTrendingRepos()
        )
    }

    override suspend fun getSearchedRepos(query: String): Resource<APIResponse> {
        return responseToResource(
            gitHubRemoteDataSource.getSearchedRepos(query)
        )
    }
    private fun responseToResource(response: Response<APIResponse>):Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}