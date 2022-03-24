package com.home.newsapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.home.newsapp.R
import com.home.newsapp.data.News
import com.home.newsapp.data.SortOrder
import com.home.newsapp.databinding.FragmentNewsBinding
import com.home.newsapp.util.Resource
import com.home.newsapp.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news), NewsAdapter.OnItemClickListener {

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsBinding.bind(view)

        val newsAdapter = NewsAdapter(this)
        binding.apply {
            recyclerViewNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            viewModel.news.observe(viewLifecycleOwner) { result ->
                newsAdapter.submitList(result.data)

                fragmentNewsProgressBar.isVisible =
                    result is Resource.Loading && result.data.isNullOrEmpty()

                fragmentNewsTextViewError.isVisible =
                    result is Resource.Error && result.data.isNullOrEmpty()
                fragmentNewsTextViewError.text = result.error?.localizedMessage
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onItemClick(news: News) {
        viewModel.onNewsClicked(news)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_news, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if(pendingQuery != null && pendingQuery.isNotEmpty()){
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged { query ->
            viewModel.searchQuery.value = query
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_sort_by_news_site -> {
                viewModel.onSortOrderSelected(SortOrder.BY_NEWSSITE)
                true
            }
            R.id.action_sort_by_title -> {
                viewModel.onSortOrderSelected(SortOrder.BY_TITLE)
                true
            }
            R.id.action_sort_by_date_created -> {
                viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}