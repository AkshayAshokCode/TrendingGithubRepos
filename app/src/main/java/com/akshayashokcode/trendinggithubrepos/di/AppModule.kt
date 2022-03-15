package com.akshayashokcode.trendinggithubrepos.di

import com.akshayashokcode.trendinggithubrepos.data.api.GitHubAPIService
import com.akshayashokcode.trendinggithubrepos.data.repository.GitHubRepositoryImpl
import com.akshayashokcode.trendinggithubrepos.data.repository.dataSource.GitHubRemoteDataSource
import com.akshayashokcode.trendinggithubrepos.data.repository.dataSourceImpl.GitHubRemoteDataSourceImpl
import com.akshayashokcode.trendinggithubrepos.domain.repository.GitHubRepository
import com.akshayashokcode.trendinggithubrepos.domain.usecase.GetSearchedReposUseCase
import com.akshayashokcode.trendinggithubrepos.domain.usecase.GetTrendingReposUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubAPIService(retrofit: Retrofit):GitHubAPIService{
        return retrofit.create(GitHubAPIService::class.java)
    }

    // Remote data
    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(gitHubAPIService: GitHubAPIService):GitHubRemoteDataSource{
        return GitHubRemoteDataSourceImpl(gitHubAPIService)
    }

    // Repository
    @Provides
    @Singleton
    fun provideGitHubRepository(
        gitHubRemoteDataSource: GitHubRemoteDataSource,
    ):GitHubRepository{
        return GitHubRepositoryImpl(gitHubRemoteDataSource)
    }

    // Use-cases
    @Provides
    @Singleton
    fun provideGetTrendingReposUseCase(
        gitHubRepository: GitHubRepository
    ):GetTrendingReposUseCase{
        return GetTrendingReposUseCase(gitHubRepository)
    }

    @Provides
    @Singleton
    fun provideGetSearchedReposUseCase(
        gitHubRepository: GitHubRepository
    ):GetSearchedReposUseCase{
        return GetSearchedReposUseCase(gitHubRepository)
    }
}