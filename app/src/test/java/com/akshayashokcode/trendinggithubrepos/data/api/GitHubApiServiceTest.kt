package com.akshayashokcode.trendinggithubrepos.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubApiServiceTest {
    private lateinit var service: GitHubAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubAPIService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    // Trending GitHub Repos
    @Test
    fun getTrendingRepos_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("githubresponse.json")
            val responseBody = service.getTrendingRepos("created:\">2022-02-16\"", 1).body()
            val request=server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/search/repositories?q=created%3A%22%3E2022-02-16%22&page=1&sort=stars")
        }
    }

    @Test
    fun getTrendingRepos_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("githubresponse.json")
            val responseBody = service.getTrendingRepos("created:\">2022-02-16\"", 1).body()
            val articlesList=responseBody!!.items
            assertThat(articlesList.size).isEqualTo(30)
        }
    }

    @Test
    fun getTrendingRepos_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("githubresponse.json")
            val responseBody = service.getTrendingRepos("created:\">2022-02-16\"", 1).body()
            val repoList=responseBody!!.items
            val repo=repoList[0]
            assertThat(repo.id).isEqualTo(463967516)
            assertThat(repo.fullName).isEqualTo("stop-war-in-ukraine/stop-russia-it")
            assertThat(repo.description).isEqualTo("An open letter from IT industry specialists to protect Ukraine from information warfare. Відкритий лист спеціалістів IT-індустрії на захист України від інформаційної війни.")
            assertThat(repo.stargazersCount).isEqualTo(7298)
            assertThat(repo.forksCount).isEqualTo(663)
        }
    }

    // Searched GitHub Repos
    @Test
    fun getSearchedRepos_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("githubresponse.json")
            val responseBody = service.getSearchedRepos("created:\">2022-02-16\"", 1).body()
            val request=server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/search/repositories?q=created%3A%22%3E2022-02-16%22&page=1&sort=stars")
        }
    }

    @Test
    fun getSearchedRepos_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("githubresponse.json")
            val responseBody = service.getSearchedRepos("created:\">2022-02-16\"", 1).body()
            val articlesList=responseBody!!.items
            assertThat(articlesList.size).isEqualTo(30)
        }
    }

    @Test
    fun getSearchedRepos_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("githubresponse.json")
            val responseBody = service.getSearchedRepos("created:\">2022-02-16\"", 1).body()
            val repoList=responseBody!!.items
            val repo=repoList[1]
            assertThat(repo.id).isEqualTo(465303307)
            assertThat(repo.fullName).isEqualTo("samber/lo")
            assertThat(repo.description).isEqualTo("\uD83D\uDCA5  A Lodash-style Go library based on Go 1.18+ Generics (map, filter, contains, find...)")
            assertThat(repo.stargazersCount).isEqualTo(3585)
            assertThat(repo.forksCount).isEqualTo(92)
        }
    }
}