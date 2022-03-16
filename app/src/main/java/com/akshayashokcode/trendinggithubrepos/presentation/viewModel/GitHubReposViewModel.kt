package com.akshayashokcode.trendinggithubrepos.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akshayashokcode.trendinggithubrepos.data.model.APIResponse
import com.akshayashokcode.trendinggithubrepos.data.util.Resource
import com.akshayashokcode.trendinggithubrepos.domain.repository.GitHubRepository
import com.akshayashokcode.trendinggithubrepos.domain.usecase.GetSearchedReposUseCase
import com.akshayashokcode.trendinggithubrepos.domain.usecase.GetTrendingReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubReposViewModel @Inject constructor(
    private val getTrendingReposUseCase: GetTrendingReposUseCase,
    private val getSearchedReposUseCase: GetSearchedReposUseCase,
    private val app: Application
):AndroidViewModel(app){

    val gitHubRepos: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getGitHubTrendingRepos(page:Int)=viewModelScope.launch(IO){
        gitHubRepos.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)) {
                val apiResult = getTrendingReposUseCase.execute(page)
                gitHubRepos.postValue(apiResult)
            }else{
                gitHubRepos.postValue(Resource.Error("Internet is not available"))
            }
        }catch (e:Exception){
            gitHubRepos.postValue(Resource.Error(e.message.toString()))
        }
    }

    //Search
    val searchedRepos:MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchRepos(
        searchQuery:String,
        page:Int
    )=viewModelScope.launch {
        searchedRepos.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getSearchedReposUseCase.execute(searchQuery,page)
                searchedRepos.postValue(response)
            } else {
                searchedRepos.postValue(Resource.Error("No internet connection"))
            }
        }catch (e:Exception){
            searchedRepos.postValue(Resource.Error(e.message.toString()))
        }
    }


    private fun  isNetworkAvailable(context: Context?):Boolean{
        if(context==null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

}