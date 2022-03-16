package com.akshayashokcode.trendinggithubrepos

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akshayashokcode.trendinggithubrepos.data.util.Resource
import com.akshayashokcode.trendinggithubrepos.databinding.ActivityMainBinding
import com.akshayashokcode.trendinggithubrepos.presentation.adapter.GitHubRepoAdapter
import com.akshayashokcode.trendinggithubrepos.presentation.viewModel.GitHubReposViewModel
import com.akshayashokcode.trendinggithubrepos.presentation.viewModel.GitHubReposViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: GitHubReposViewModelFactory

    @Inject
    lateinit var gitHubAdapter: GitHubRepoAdapter
    private lateinit var viewModel: GitHubReposViewModel
    private lateinit var binding: ActivityMainBinding
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isScrollLoading = false
    private var isLastPage = false
    private var pages = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[GitHubReposViewModel::class.java]

        initRecyclerview()
        viewRepoList()
        setSearchView()
    }

    private fun initRecyclerview() {
        binding.recyclerView.apply {
            adapter = gitHubAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addOnScrollListener(this@MainActivity.onScrollListener)
        }
    }

    private fun viewRepoList() {
        viewModel.getGitHubTrendingRepos(page)
        viewModel.gitHubRepos.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        gitHubAdapter.setList(it.items.toList())
                        // Default results per page is 30
                        if (it.totalCount % 30 == 0) {
                            pages = it.totalCount / 30
                        } else {
                            pages = it.totalCount / 30 + 1
                        }
                        isLastPage = page == pages

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.d("MainActivity","Error:$it")
                        //    Toast.makeText(this, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    //search
    private fun setSearchView() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchRepos(p0.toString(), page)
                viewSearchedRepos()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(1500)
                    viewModel.searchRepos(p0.toString(), page)
                    viewSearchedRepos()
                    if (p0.toString().isEmpty()) {
                       // gitHubAdapter.clearList()
                        viewRepoList()
                        binding.search.clearFocus()
                    }
                }
                return false

            }
        })
        binding.search.setOnCloseListener {
            initRecyclerview()
            //gitHubAdapter.clearList()
            viewRepoList()
            binding.search.clearFocus()
            false
        }
    }

    fun viewSearchedRepos() {
        viewModel.searchedRepos.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        // gitHubAdapter.clearList()

                        // Default results per page is 30
                        gitHubAdapter.setList(it.items.toList())
                        if (it.totalCount % 30 == 0) {
                            pages = it.totalCount / 30
                        } else {
                            pages = it.totalCount / 30 + 1
                        }
                        isLastPage = page == pages

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.d("MainActivity","Error:$it")
                        //   Toast.makeText(this, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            Log.d("MainActivity", "total items:${gitHubAdapter.itemCount}")
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
                hideScrollProgressBar()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if (shouldPaginate) {
                page++
                showScrollProgressBar()
                viewModel.getGitHubTrendingRepos(page)
                isScrolling = false
            }
        }
    }

    private fun showProgressBar() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showScrollProgressBar() {
        isScrollLoading = true
        binding.scrollProgressBar.visibility = View.VISIBLE
    }

    private fun hideScrollProgressBar() {
        isScrollLoading = false
        binding.scrollProgressBar.visibility = View.INVISIBLE
    }
}