package com.akshayashokcode.trendinggithubrepos.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akshayashokcode.trendinggithubrepos.domain.usecase.GetSearchedReposUseCase
import com.akshayashokcode.trendinggithubrepos.domain.usecase.GetTrendingReposUseCase

class GitHubReposViewModelFactory(
    private val getTrendingReposUseCase: GetTrendingReposUseCase,
    private val getSearchedReposUseCase: GetSearchedReposUseCase,
    private val app: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitHubReposViewModel(
            getTrendingReposUseCase,
            getSearchedReposUseCase,
            app
        ) as T
    }
}