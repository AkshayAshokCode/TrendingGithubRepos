package com.akshayashokcode.trendinggithubrepos.data.repository.dataSourceImpl

import android.util.Log
import com.akshayashokcode.trendinggithubrepos.data.api.GitHubAPIService
import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import com.akshayashokcode.trendinggithubrepos.data.repository.dataSource.GitHubRemoteDataSource
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class GitHubRemoteDataSourceImpl(
    private val gitHubAPIService: GitHubAPIService,
) : GitHubRemoteDataSource {
    override suspend fun getTrendingRepos(): Response<APIResponse> {
        val calender= Calendar.getInstance()
        calender.add(Calendar.MONTH,-1)
        val date=calender.time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateNow = sdf.format(date)
       // Log.d("dateNow","Current Date:$dateNow")
        return gitHubAPIService.getTrendingRepos("created:\">$dateNow\"")
    }

    override suspend fun getSearchedRepos(query: String): Response<APIResponse> {
        return gitHubAPIService.getSearchedRepos(query)
    }
}